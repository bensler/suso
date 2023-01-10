package com.bensler.suso;

import java.util.Optional;

public interface Field {

  Optional<Digit> get(Coordinate coordinate);

}
