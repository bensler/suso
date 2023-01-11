package com.bensler.suso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FieldImpl implements Field.Mutable {

  private final Map<Coordinate, Digit> data;

  public FieldImpl(Map<Coordinate, Digit> pData) {
    data = new HashMap<>(pData);
  }

  public FieldImpl(FieldImpl pField) {
    data = new HashMap<>(pField.data);
  }

  @Override
  public Optional<Digit> get(Coordinate coordinate) {
    return Optional.ofNullable(data.get(coordinate));
  }

  @Override
  public Set<Coordinate> getSetCoordinates() {
    return new HashSet<>(data.keySet());
  }

  @Override
  public void set(Coordinate coordinate, Digit digit) {
    if (digit == null) {
      throw new IllegalArgumentException("digit must not be null");
    }
    data.put(coordinate, digit);
  }

  @Override
  public String toString() {
    return FieldPrinter.INSTANCE.print(this);
  }

  public boolean equals(FieldImpl other) {
    return data.equals(other.data);
  }

}
