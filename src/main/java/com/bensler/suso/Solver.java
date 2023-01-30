package com.bensler.suso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Solver {

  private final Game game;
  private final Set<Constraint> constraints;

  public Solver(Game pGame) {
    game = pGame;
    constraints = game.getConstraints();
  }

  public boolean solve() {
    final Set<Coordinate> emptyCells = game.getEmptyCells();

    while (!emptyCells.isEmpty()) {
      Map<Coordinate, Digit> hits = new HashMap<>();
      for (Coordinate emptyCell : emptyCells) {
        final Set<Digit> possibleDigits = findPossibleDigits(emptyCell);

        if (possibleDigits.size() == 1) {
          hits.put(emptyCell, possibleDigits.stream().findFirst().get());
        }
      }
      System.out.println(hits);
      if (hits.isEmpty()) {
        return false;
      } else {
        hits.forEach((coordinate, digit) -> {
          game.setDigit(coordinate, Optional.of(digit));
          emptyCells.remove(coordinate);
        });
      }
    }
    return true;
  }

  public Set<Digit> findPossibleDigits(Coordinate coordinate) {
    final Set<Digit> unusedDigits = new HashSet<>(Digit.VALUES);

    constraints.stream()
      .filter(constraint -> constraint.covers(coordinate))
      .map(constraint -> constraint.getUsedDigits(game))
      .forEach(digits -> unusedDigits.removeAll(digits));
    return unusedDigits;
  }

}