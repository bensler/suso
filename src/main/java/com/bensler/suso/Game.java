package com.bensler.suso;

import java.awt.Rectangle;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Game implements Field.Mutable {

  private final int width;
  private final int height;
  private final Set<Coordinate> coordinates;
  private final FieldImpl field;
  private final List<Constraint> constraints;

  public Game(Game template) {
    width = template.width;
    height = template.height;
    coordinates = new HashSet<>(template.coordinates);
    constraints = new ArrayList<>(template.constraints);
    field = new FieldImpl(template.field);
  }

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
    field = new FieldImpl(new FieldCreator(coordinates).createFieldData(initialState));
  }

  private Constraint createConstraint(Rectangle rect) {
    return new Constraint(createCoordinates(rect));
  }

  private Set<Coordinate> createCoordinates(Rectangle rect) {
    return Set.copyOf(IntStream.range(rect.x, rect.x + rect.width).mapToObj(x -> IntStream.range(rect.y, rect.y + rect.height)
      .mapToObj(y -> new Coordinate(x, y)).collect(Collectors.toSet()))
      .flatMap(coordinateList -> coordinateList.stream()).collect(Collectors.toSet()
    ));
  }

  public Set<Coordinate> getEmptyCells() {
    return new HashSet<>(
      coordinates.stream()
      .filter(coordinate -> getDigit(coordinate).isEmpty())
      .collect(Collectors.toSet())
    );
  }

  public Map<Constraint, Set<Digit>> getFailingConstraints() {
    return constraints.stream().map(
      constraint -> new SimpleImmutableEntry<>(
        constraint,
        constraint.findDuplicateDigits(field)
      )
    ).filter(pair -> !pair.getValue().isEmpty())
    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
  }

  public boolean isValid() {
    return !constraints.stream().anyMatch(
      constraint -> !constraint.check(field)
    );
  }

  public Set<Coordinate> getCoordinates() {
    return new HashSet<>(coordinates);
  }

  public Set<Constraint> getConstraints() {
    return new HashSet<>(constraints);
  }

  @Override
  public Optional<Digit> getDigit(Coordinate coordinate) {
    return field.getDigit(coordinate);
  }

  @Override
  public Set<Coordinate> getSetCoordinates() {
    return field.getSetCoordinates();
  }

  @Override
  public void setDigit(Coordinate coordinate, Optional<Digit> digit) {
    field.setDigit(coordinate, digit);
  }

  public void solve() {
    if (!isValid()) {
      throw new IllegalStateException("game is invalid");
    }
    new Solver(this).solve();
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
