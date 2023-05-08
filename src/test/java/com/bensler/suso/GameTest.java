package com.bensler.suso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.bensler.suso.Solver.Solvability;

public class GameTest {

  private final static int[][] TO_SOLVE_1 = new int[][] {
    {5, 3, 0,  0, 7, 0,  0, 0, 0},
    {6, 0, 0,  1, 9, 5,  0, 0, 0},
    {0, 9, 8,  0, 0, 0,  0, 6, 0},

    {8, 0, 0,  0, 6, 0,  0, 0, 3},
    {4, 0, 0,  8, 5, 3,  0, 0, 1},
    {7, 0, 0,  0, 2, 0,  0, 0, 6},

    {0, 6, 0,  0, 0, 0,  2, 8, 0},
    {0, 0, 0,  4, 1, 9,  0, 0, 5},
    {0, 0, 0,  0, 8, 0,  0, 7, 9}
  };

  private final static int[][] SOLVED_1 = new int[][] {
    {5, 3, 4,  6, 7, 8,  9, 1, 2},
    {6, 7, 2,  1, 9, 5,  3, 4, 8},
    {1, 9, 8,  3, 4, 2,  5, 6, 7},

    {8, 5, 9,  7, 6, 1,  4, 2, 3},
    {4, 2, 6,  8, 5, 3,  7, 9, 1},
    {7, 1, 3,  9, 2, 4,  8, 5, 6},

    {9, 6, 1,  5, 3, 7,  2, 8, 4},
    {2, 8, 7,  4, 1, 9,  6, 3, 5},
    {3, 4, 5,  2, 8, 6,  1, 7, 9}
  };

  @Test
  public void testValid() {
    assertTrue(new Game(TO_SOLVE_1).isValid());
  }

  @Test
  public void testSolvedValid() {
    assertTrue(new Game(SOLVED_1).isValid());
  }

  @Test
  public void testSolve1() {
    final Game game = new Game(TO_SOLVE_1);

    game.solve();
    assertTrue(game.getField().equals(new Game(SOLVED_1).getField()));
  }

  public void testSolveSolved() {
    final Game game = new Game(SOLVED_1);
    final FieldImpl fieldBefore = game.getField();

    game.solve();
    assertTrue(fieldBefore.equals(game.getField()));
  }

  @Test
  public void testNotDetermined() {
    final Game game = new Game(new int[][] {
      {5, 0, 0,  0, 0, 0,  0, 0, 0},
      {0, 0, 0,  0, 0, 0,  0, 0, 0},
      {0, 0, 0,  0, 0, 0,  0, 0, 0},

      {0, 0, 0,  0, 0, 0,  0, 0, 0},
      {0, 0, 0,  0, 0, 0,  0, 0, 0},
      {0, 0, 0,  0, 0, 0,  0, 0, 0},

      {0, 0, 0,  0, 0, 0,  0, 0, 0},
      {0, 0, 0,  0, 0, 0,  0, 0, 0},
      {0, 0, 0,  0, 0, 0,  0, 0, 0}
    });

    assertEquals(Solvability.NOT_DETERMINED, new Solver(game).solve());
  }

  @Test
  public void testRowWrong() {
    final Game game = new Game(new int[][] {
      // ------------------------v
      {5, 3, 0,  0, 7, 0,  0, 0, 7},
      {6, 0, 0,  1, 9, 5,  0, 0, 0},
      {0, 9, 8,  0, 0, 0,  0, 6, 0},

      {8, 0, 0,  0, 6, 0,  0, 0, 3},
      {4, 0, 0,  8, 5, 3,  0, 0, 1},
      {7, 0, 0,  0, 2, 0,  0, 0, 6},

      {0, 6, 0,  0, 0, 0,  2, 8, 0},
      {0, 0, 0,  4, 1, 9,  0, 0, 5},
      {0, 0, 0,  0, 8, 0,  0, 7, 9}
    });

    if (game.isValid()) {
      fail("game should not be valid");
    } else {
      assertEquals(1, game.getFailingConstraints().size());
    }
  }

  @Test
  public void testColWrong() {
    final Game game = new Game(new int[][] {
      {5, 3, 0,  0, 7, 0,  0, 0, 0},
      {6, 0, 0,  1, 9, 5,  0, 0, 0},
      {0, 9, 8,  0, 0, 0,  2, 6, 0},
      // ------------------^
      {8, 0, 0,  0, 6, 0,  0, 0, 3},
      {4, 0, 0,  8, 5, 3,  0, 0, 1},
      {7, 0, 0,  0, 2, 0,  0, 0, 6},

      {0, 6, 0,  0, 0, 0,  2, 8, 0},
      {0, 0, 0,  4, 1, 9,  0, 0, 5},
      {0, 0, 0,  0, 8, 0,  0, 7, 9}
    });

    if (game.isValid()) {
      fail("game should not be valid");
    } else {
      assertEquals(1, game.getFailingConstraints().size());
    }
  }

  @Test
  public void testSquareWrong() {
    final Game game = new Game(new int[][] {
      {5, 3, 0,  0, 7, 0,  0, 0, 0},
      {6, 0, 0,  1, 9, 5,  0, 0, 0},
      {0, 9, 8,  0, 0, 0,  0, 6, 0},
      // --------v
      {8, 0, 0,  5, 6, 0,  0, 0, 3},
      {4, 0, 0,  8, 5, 3,  0, 0, 1},
      {7, 0, 0,  0, 2, 0,  0, 0, 6},

      {0, 6, 0,  0, 0, 0,  2, 8, 0},
      {0, 0, 0,  4, 1, 9,  0, 0, 5},
      {0, 0, 0,  0, 8, 0,  0, 7, 9}
    });

    if (game.isValid()) {
      fail("game should not be valid");
    } else {
      assertEquals(1, game.getFailingConstraints().size());
    }
  }

  @Test
  public void testViolateMultipleConstraints() {
    final Game game = new Game(new int[][] {
      {5, 3, 0,  0, 7, 0,  0, 0, 0},
      // ------------------------v
      {6, 0, 0,  1, 9, 5,  0, 0, 6},
      {0, 9, 8,  0, 0, 0,  0, 6, 0},

      {8, 0, 0,  0, 6, 0,  0, 0, 3},
      {4, 0, 0,  8, 5, 3,  0, 0, 1},
      {7, 0, 0,  0, 2, 0,  0, 0, 6},

      {0, 6, 0,  0, 0, 0,  2, 8, 0},
      {0, 0, 0,  4, 1, 9,  0, 0, 5},
      {0, 0, 0,  0, 8, 0,  0, 7, 9}
    });

    if (game.isValid()) {
      fail("game should not be valid");
    } else {
      assertEquals(3, game.getFailingConstraints().size());
    }
  }

}
