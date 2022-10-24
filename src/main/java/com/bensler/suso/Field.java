package com.bensler.suso;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Field {

  public enum Digit {
    _1(1),
    _2(2),
    _3(3),
    _4(4),
    _5(5),
    _6(6),
    _7(7),
    _8(8),
    _9(9);

    public final static Set<Digit> VALUES = Set.of(Digit.values());

    private final int number;

    private Digit(int pNumber) {
      number = pNumber;
    }

  }

  private final static Map<Integer, Digit> DigitsLookup = Arrays.stream(Digit.values()).collect(Collectors.toMap(
    digit -> Integer.valueOf(digit.number),
    digit -> digit
  ));

  private final List<List<Digit>> field;

  public Field(int[][] initialState) {
    // TODO range check 9x9
    // TODO range check 1..9
    field = Arrays.stream(initialState).map(
      row -> Arrays.stream(row).mapToObj(
        col -> DigitsLookup.get(col)
      ).collect(Collectors.toList())
    ).collect(Collectors.toList());
  }

  public boolean checkConstraints(List<Constraint> constraints) {
    return constraints.stream().filter(constraint -> !constraint.check(this)).findFirst().isEmpty();
  }

  public Digit get(int vx, int vy) {
    // TODO range check
    return field.get(vy).get(vx);
  }

}
