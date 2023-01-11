package com.bensler.suso;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

class Solver {

  private final Game game;
  private final Set<Constraint> constraints;

  public Solver(Game pGame) {
    game = pGame;
    constraints = game.getConstraints();
  }

  public void solve() {
    final Set<Coordinate> emptyCells = new HashSet<>(
      game.getCoordinates().stream()
      .filter(coordinate -> game.get(coordinate).isEmpty())
      .collect(Collectors.toSet())
    );

    while (!emptyCells.isEmpty()) {
      Map<Coordinate, Digit> hits = new HashMap<>();
      for (Coordinate emptyCell : emptyCells) {
        final Optional<Digit> hit = trySolve(emptyCell);

        if (hit.isPresent()) {
          hits.put(emptyCell, hit.get());
        }
      }
      System.out.println(hits);
      if (hits.isEmpty()) {
        throw new IllegalStateException("Unsolvable!");
      } else {
        hits.forEach((coordinate, digit) -> {
          game.set(coordinate, digit);
          emptyCells.remove(coordinate);
        });
      }
    }
  }

  private Optional<Digit> trySolve(Coordinate coordinate) {
    final List<Constraint> applicableConstraints = constraints.stream()
      .filter(constraint -> constraint.applies(coordinate))
      .collect(Collectors.toList());
    final Map<Constraint, Set<Digit>> usedDigitsPerConstraint = applicableConstraints.stream().collect(Collectors.toMap(
      constraint -> constraint,
      constraint -> constraint.getUsedDigits(game)
    ));
    final Set<Digit> allDigits = new HashSet<>(Digit.VALUES);

    usedDigitsPerConstraint.values().stream().forEach(usedDigits -> allDigits.removeAll(usedDigits));
    return ((allDigits.size() == 1) ? Optional.of(allDigits.stream().findFirst().get()) : Optional.empty());
  }

}