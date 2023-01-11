package com.bensler.suso;

import java.util.Optional;
import java.util.Set;

public interface Field {

  Optional<Digit> get(Coordinate coordinate);

  Set<Coordinate> getSetCoordinates();

  interface Mutable extends Field {

    void set(Coordinate coordinate, Digit digit);

  }

}
