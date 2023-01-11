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

public class FieldImpl implements Field.Mutable {

  private final static Map<Integer, Digit> DigitsLookup = Arrays.stream(Digit.values()).collect(Collectors.toMap(
    digit -> Integer.valueOf(digit.number),
    digit -> digit
  ));

  private final Map<Coordinate, Digit> field;

  public FieldImpl(Set<Coordinate> coordinates, int[][] initialState) {
    field = coordinates.stream().map(
      coordinate -> new SimpleEntry<>(coordinate, DigitsLookup.get(initialState[coordinate.getY()][coordinate.getX()]))
    ).filter(entry -> (entry.getValue() != null))
    .collect(Collectors.toMap(
      entry -> entry.getKey(),
      entry -> entry.getValue()
    ));
  }

  public FieldImpl(FieldImpl pField) {
    field = new HashMap<>(pField.field);
  }

  @Override
  public Optional<Digit> get(Coordinate coordinate) {
    return Optional.ofNullable(field.get(coordinate));
  }

  @Override
  public void set(Coordinate coordinate, Digit digit) {
    field.put(coordinate, digit);
  }

  @Override
  public String toString() {
    return field.entrySet().stream().sorted(
      (entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey())
    ).map(
      entry -> entry.getKey() + ":" + entry.getValue()
    ).collect(Collectors.joining("\n"));
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

  public boolean equals(FieldImpl other) {
    return field.equals(other.field);
  }

}
