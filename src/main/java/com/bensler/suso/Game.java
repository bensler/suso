package com.bensler.suso;

import java.awt.Rectangle;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game {

  private final int width;
  private final int height;
  private final Set<Coordinate> coordinates;
  private final FieldImpl field;
  private final List<Constraint> constraints;

  public Game(int[][] initialState) {
    width = 9;
    height = 9;
    coordinates = createCoordinates(new Rectangle(0, 0, width, height));
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
    field = new FieldImpl(coordinates, initialState);
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
    return new HashSet<>(coordinates);
  }

  public void validate() throws ValidationException {
    final Set<Constraint> failedConstraints = constraints.stream().filter(
      constraint -> !constraint.check(field)
    ).collect(Collectors.toSet());

    if (!failedConstraints.isEmpty()) {
      throw new ValidationException(failedConstraints);
    }
  }

  public void solve() throws ValidationException {
    validate();
    final Set<Coordinate> emptyCells = new HashSet<>(
      coordinates.stream()
      .filter(coordinate -> field.get(coordinate).isEmpty())
      .collect(Collectors.toSet())
    );

    while (!emptyCells.isEmpty()) {
      Map<Coordinate, Digit> hits = new HashMap<>();
      for (Coordinate emptyCell : emptyCells) {
        final Optional<Digit> hit = field.trySolve(emptyCell, constraints);

        if (hit.isPresent()) {
          hits.put(emptyCell, hit.get());
        }
      }
      System.out.println(hits);
      if (hits.isEmpty()) {
        throw new IllegalStateException("Unsolvable!");
      } else {
        hits.forEach((coordinate, digit) -> {
          field.set(coordinate, digit);
          emptyCells.remove(coordinate);
        });
      }
    }
  }

  public FieldImpl getField() {
    return new FieldImpl(field);
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

}
