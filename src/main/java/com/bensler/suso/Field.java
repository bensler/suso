package com.bensler.suso;

import java.util.Optional;
import java.util.Set;

public interface Field {

  Optional<Digit> getDigit(Coordinate coordinate);

  Set<Coordinate> getSetCoordinates();

  interface Mutable extends Field {

    void setDigit(Coordinate coordinate, Digit digit);

  }

}
