package com.bensler.suso;

import java.awt.Rectangle;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Constraint {

  private final Set<Coordinate> coordinates;

  public Constraint(Set<Coordinate> pCoordinates) {
    coordinates = Set.copyOf(pCoordinates);
  }

  /** @return if constraint is fulfilled */
  public boolean check(Field field) {
    final List<Digit> collectedDigits =  coordinates.stream().flatMap(
      coordinate -> field.getDigit(coordinate).stream()
    ).collect(Collectors.toList());
    // duplicate check
    return !(collectedDigits.size() > Set.copyOf(collectedDigits).size());
  }

  public boolean applies(Coordinate coordinate) {
    return coordinates.contains(coordinate);
  }

  public Set<Digit> getUsedDigits(Field field) {
    return coordinates.stream().flatMap(coordinate -> field.getDigit(coordinate).stream()).collect(Collectors.toSet());
  }

  public Rectangle getBounds() {
    final int minX = coordinates.stream().mapToInt(Coordinate::getX).min().getAsInt();
    final int maxX = coordinates.stream().mapToInt(Coordinate::getX).max().getAsInt();
    final int minY = coordinates.stream().mapToInt(Coordinate::getY).min().getAsInt();
    final int maxY = coordinates.stream().mapToInt(Coordinate::getY).max().getAsInt();

    return new Rectangle(minX, minY, maxX - minX, maxY - minY);
  }

}
