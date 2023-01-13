package com.bensler.suso;

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

    return !(collectedDigits.size() > Set.copyOf(collectedDigits).size());
  }

  public boolean applies(Coordinate coordinate) {
    return coordinates.contains(coordinate);
  }

  public Set<Digit> getUsedDigits(Field field) {
    return coordinates.stream().flatMap(coordinate -> field.getDigit(coordinate).stream()).collect(Collectors.toSet());
  }

}
