package com.bensler.suso;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class Field {

  private final static Map<Integer, Digit> DigitsLookup = Arrays.stream(Digit.values()).collect(Collectors.toMap(
    digit -> Integer.valueOf(digit.number),
    digit -> digit
  ));

  private final Map<Coordinate, Digit> field;

  public Field(Set<Coordinate> coordinates, int[][] initialState) {
    field = coordinates.stream().map(
      coordinate -> new SimpleEntry<>(coordinate, DigitsLookup.get(initialState[coordinate.getY()][coordinate.getX()]))
    ).filter(entry -> (entry.getValue() != null))
    .collect(Collectors.toMap(
      entry -> entry.getKey(),
      entry -> entry.getValue()
    ));
  }

  public Field(Field pField) {
    field = new HashMap<>(pField.field);
  }

  public void checkConstraints(List<Constraint> constraints) throws ValidationException {
    final Set<Constraint> failedConstraints = constraints.stream().filter(
      constraint -> !constraint.check(this)
    ).collect(Collectors.toSet());

    if (!failedConstraints.isEmpty()) {
      throw new ValidationException(failedConstraints);
    }
  }

  public Optional<Digit> get(Coordinate coordinate) {
    return Optional.ofNullable(field.get(coordinate));
  }

  public void set(Coordinate coordinate, Digit digit) {
    if (field.containsKey(coordinate)) {
      throw new IllegalArgumentException("Already set!");
    } else {
      field.put(coordinate, digit);
    }
  }

  @Override
  public String toString() {
    return field.entrySet().stream().sorted(
      (entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey())
    ).map(
      entry -> entry.getKey() + ":" + entry.getValue()
    ).collect(Collectors.joining("\n"));
  }

  public Set<Coordinate> getEmptyCells(Set<Coordinate> allCoordinates) {
    allCoordinates.removeAll(field.keySet());
    return allCoordinates;
  }

  public Optional<Digit> trySolve(Coordinate coordinate, Collection<Constraint> constraints) {
    if (field.containsKey(coordinate)) {
      throw new IllegalArgumentException("Already solved");
    } else {
      final List<Constraint> applicableConstraints = constraints.stream()
      .filter(constraint -> constraint.applies(coordinate))
      .collect(Collectors.toList());
      final Map<Constraint, Set<Digit>> usedDigitsPerConstraint = applicableConstraints.stream().collect(Collectors.toMap(
        constraint -> constraint,
        constraint -> constraint.getUsedDigits(this)
      ));
      final Set<Digit> allDigits = new HashSet<>(Digit.VALUES);

      usedDigitsPerConstraint.values().stream().forEach(usedDigits -> allDigits.removeAll(usedDigits));
      return ((allDigits.size() == 1) ? Optional.of(allDigits.stream().findFirst().get()) : Optional.empty());
    }
  }

  public boolean equals(Field other) {
    return field.equals(other.field);
  }

}
