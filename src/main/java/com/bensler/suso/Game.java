package com.bensler.suso;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    constraints = Stream.concat(
      Stream.concat(
        // rows
        IntStream.range(0, 9).mapToObj(rowIndex -> new Constraint(0, rowIndex, 9, 1)),
        // columns
        IntStream.range(0, 9).mapToObj(colIndex -> new Constraint(colIndex, 0, 1, 9))
      ),
      Stream.of(
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
      )
    ).collect(Collectors.toList());
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
