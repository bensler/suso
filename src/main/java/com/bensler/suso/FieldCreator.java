package com.bensler.suso;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FieldCreator {

  private final Map<Integer, Digit> digitsLookup;
  private final Set<Coordinate> coordinates;

  public FieldCreator(Set<Coordinate> pCoordinates) {
    digitsLookup = Arrays.stream(Digit.values()).collect(Collectors.toMap(
      digit -> Integer.valueOf(digit.number),
      digit -> digit
    ));
    coordinates = pCoordinates;
  }

  public Map<Coordinate, Digit> createFieldData(int[][] initialState) {
    return coordinates.stream().map(
      coordinate -> new SimpleEntry<>(coordinate, digitsLookup.get(initialState[coordinate.getY()][coordinate.getX()]))
    ).filter(entry -> (entry.getValue() != null))
    .collect(Collectors.toMap(
      entry -> entry.getKey(),
      entry -> entry.getValue()
    ));
  }

}
