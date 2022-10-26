package com.bensler.suso;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.bensler.suso.Field.Coordinate;

public class Game {

  private final Set<Coordinate> coordinates;
  private final Field field;
  private final List<Constraint> constraints;

  public Game(int[][] initialState) {
    coordinates = Set.copyOf(IntStream.range(0, 9).mapToObj(x -> IntStream.range(0, 9)
      .mapToObj(y -> new Coordinate(x, y)).collect(Collectors.toSet()))
      .flatMap(coordinateList -> coordinateList.stream()).collect(Collectors.toSet()
    ));
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
    field = new Field(coordinates, initialState);
  }

  public Set<Coordinate> getCoordinates() {
    return coordinates;
  }

  public void validate() throws ValidationException {
    field.checkConstraints(constraints);
  }

  public void solve() {
    // TODO

  }

}
