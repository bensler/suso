package com.bensler.suso;

import java.awt.Rectangle;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Constraint {

  private final Rectangle rect;
  private final Set<Coordinate> coordinates;

  public Constraint(Rectangle pRect, Set<Coordinate> pCoordinates) {
    rect = new Rectangle(pRect);
    coordinates = Set.copyOf(pCoordinates);
  }

  /** @return if constraint is fulfilled */
  public boolean check(Field field) {
    final List<Digit> collectedDigits =  coordinates.stream().flatMap(
      coordinate -> field.get(coordinate).stream()
    ).collect(Collectors.toList());

    return !(collectedDigits.size() > Set.copyOf(collectedDigits).size());
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + rect + "]";
  }

  public boolean applies(Coordinate coordinate) {
    return coordinates.contains(coordinate);
  }

  public Set<Digit> getUsedDigits(FieldImpl field) {
    return coordinates.stream().flatMap(coordinate -> field.get(coordinate).stream()).collect(Collectors.toSet());
  }

}
