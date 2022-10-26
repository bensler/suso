package com.bensler.suso;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Field {

  public enum Digit {
    _1_(1),
    _2_(2),
    _3_(3),
    _4_(4),
    _5_(5),
    _6_(6),
    _7_(7),
    _8_(8),
    _9_(9);

    public final static Set<Digit> VALUES = Set.of(Digit.values());

    private final int number;

    private Digit(int pNumber) {
      number = pNumber;
    }

    @Override
    public String toString() {
      return Integer.valueOf(number).toString();
    }

  }

  public final static class Coordinate implements Comparable<Coordinate> {

    private final int x;
    private final int y;

    public Coordinate(int pX, int pY) {
      x = pX;
      y = pY;
    }

    @Override
    public int hashCode() {
      return (10 * y) + x;
    }

    @Override
    public boolean equals(Object obj) {
      return (
        (obj != null)
        && (
          (obj == this)
          || (
            obj.getClass().equals(getClass())
            && equals((Coordinate)obj)
          )
        )
      );
    }

    public boolean equals(Coordinate other) {
      return (x == other.x) && (y == other.y);
    }

    @Override
    public int compareTo(Coordinate other) {
      final int rowCmp = y - other.y;

      return ((rowCmp != 0) ? rowCmp : (x - other.x));
    }

    @Override
    public String toString() {
      return "(" + x + ", " + y + ")";
    }

  }

  private final static Map<Integer, Digit> DigitsLookup = Arrays.stream(Digit.values()).collect(Collectors.toMap(
    digit -> Integer.valueOf(digit.number),
    digit -> digit
  ));

  private final Map<Coordinate, Digit> field;

  public Field(Set<Coordinate> coordinates, int[][] initialState) {
    field = coordinates.stream().map(
      coordinate -> new SimpleEntry<>(coordinate, DigitsLookup.get(initialState[coordinate.y][coordinate.x]))
    ).filter(entry -> (entry.getValue() != null))
    .collect(Collectors.toMap(
      entry -> entry.getKey(),
      entry -> entry.getValue()
    ));
  }

  public void checkConstraints(List<Constraint> constraints) throws ValidationException {
    final Set<Constraint> failedConstraints = constraints.stream().filter(
      constraint -> !constraint.check(this)
    ).collect(Collectors.toSet());

    if (!failedConstraints.isEmpty()) {
      throw new ValidationException(failedConstraints);
    }
  }

  public Digit get(int x, int y) {
    return field.get(new Coordinate(x, y));
  }

  @Override
  public String toString() {
    return field.entrySet().stream().sorted(
      (entry1, entry2) -> entry1.getKey().compareTo(entry2.getKey())
    ).map(
      entry -> entry.getKey() + ":" + entry.getValue()
    ).collect(Collectors.joining("\n"));
  }

}
