package com.bensler.suso;

import static com.bensler.suso.Solver.Solvability.NOT_DETERMINED;
import static com.bensler.suso.Solver.Solvability.NOT_SOLVALBE;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class Solver {

  public enum Solvability {
    SOLVABLE(true),
    NOT_SOLVALBE(false),
    NOT_DETERMINED(false);

    private final boolean solvable;

    Solvability(boolean pSolvable) {
      solvable = pSolvable;
    }

    public boolean isSolvable() {
      return solvable;
    }

  }

  private final Game game;
  private final Set<Constraint> constraints;

  public Solver(Game pGame) {
    game = pGame;
    constraints = game.getConstraints();
  }

  public Solvability solve() {
    final Set<Coordinate> emptyCells = game.getEmptyCells();

    while (!emptyCells.isEmpty()) {
      final Map<Coordinate, Digit> determinedCells = new HashMap<>();
      final Set<Coordinate> undeterminedCells = new HashSet<>();

      for (Coordinate emptyCell : emptyCells) {
        final Set<Digit> possibleDigits = findPossibleDigits(emptyCell);
        final int possibleDigitCount = possibleDigits.size();

        if (possibleDigitCount == 0) {
          return NOT_SOLVALBE;
        }
        if (possibleDigitCount == 1) {
          determinedCells.put(emptyCell, possibleDigits.stream().findFirst().get());
        } else { // (possibleDigitCount > 1)
          undeterminedCells.add(emptyCell);
        }
      }
      if (determinedCells.isEmpty()) {
        return ((undeterminedCells.isEmpty()) ? NOT_SOLVALBE : NOT_DETERMINED);
      } else {
        determinedCells.forEach((coordinate, digit) -> {
          game.setDigit(coordinate, Optional.of(digit));
          emptyCells.remove(coordinate);
        });
      }
    }
    return Solvability.SOLVABLE;
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