package com.bensler.suso;

import java.awt.Rectangle;
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
    coordinates = createCoordinates(new Rectangle(0, 0, 9, 9));
    constraints = Stream.concat(
      Stream.concat(
        // rows
        IntStream.range(0, 9).mapToObj(rowIndex -> createConstraint(new Rectangle(0, rowIndex, 9, 1))),
        // columns
        IntStream.range(0, 9).mapToObj(colIndex -> createConstraint(new Rectangle(colIndex, 0, 1, 9)))
      ),
      Stream.of(
        // squares
        createConstraint(new Rectangle(0, 0, 3, 3)),
        createConstraint(new Rectangle(3, 0, 3, 3)),
        createConstraint(new Rectangle(6, 0, 3, 3)),
        createConstraint(new Rectangle(0, 3, 3, 3)),
        createConstraint(new Rectangle(3, 3, 3, 3)),
        createConstraint(new Rectangle(6, 3, 3, 3)),
        createConstraint(new Rectangle(0, 6, 3, 3)),
        createConstraint(new Rectangle(3, 6, 3, 3)),
        createConstraint(new Rectangle(6, 6, 3, 3))
      )
    ).collect(Collectors.toList());
    field = new Field(coordinates, initialState);
  }

  private Constraint createConstraint(Rectangle rect) {
    return new Constraint(rect, createCoordinates(rect));
  }

  private Set<Coordinate> createCoordinates(Rectangle rect) {
    return Set.copyOf(IntStream.range(rect.x, rect.x + rect.width).mapToObj(x -> IntStream.range(rect.y, rect.y + rect.height)
      .mapToObj(y -> new Coordinate(x, y)).collect(Collectors.toSet()))
      .flatMap(coordinateList -> coordinateList.stream()).collect(Collectors.toSet()
    ));
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
