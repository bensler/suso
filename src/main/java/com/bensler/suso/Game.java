package com.bensler.suso;

import java.util.List;

public class Game {

  private final Field field;
  private final List<Constraint> constraints;

  public Game(int[][] initialState) {
    field = new Field(initialState);
    constraints = List.of(
      // rows
      new Constraint(0, 0, 9, 1),
      new Constraint(0, 1, 9, 1),
      new Constraint(0, 2, 9, 1),
      new Constraint(0, 3, 9, 1),
      new Constraint(0, 4, 9, 1),
      new Constraint(0, 5, 9, 1),
      new Constraint(0, 6, 9, 1),
      new Constraint(0, 7, 9, 1),
      new Constraint(0, 8, 9, 1),
      // columns
      new Constraint(0, 0, 1, 9),
      new Constraint(1, 0, 1, 9),
      new Constraint(2, 0, 1, 9),
      new Constraint(3, 0, 1, 9),
      new Constraint(4, 0, 1, 9),
      new Constraint(5, 0, 1, 9),
      new Constraint(6, 0, 1, 9),
      new Constraint(7, 0, 1, 9),
      new Constraint(8, 0, 1, 9),
      // squares
      new Constraint(0, 0, 3, 3),
      new Constraint(3, 0, 3, 3),
      new Constraint(6, 0, 3, 3),
      new Constraint(0, 3, 3, 3),
      new Constraint(3, 3, 3, 3),
      new Constraint(6, 3, 3, 3),
      new Constraint(0, 6, 3, 3),
      new Constraint(3, 6, 3, 3),
      new Constraint(6, 6, 3, 3)
    );
  }

  public void validate() throws ValidationException {
    field.checkConstraints(constraints);
  }

  public void solve() {
    // TODO

  }

}
