package com.bensler.suso;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class GameTest {

  @Test
  public void testValid() {
    new Game(new int[][] {
      {5, 3, 0,  0, 7, 0,  0, 0, 0},
      {6, 0, 0,  1, 9, 5,  0, 0, 0},
      {0, 9, 8,  0, 0, 0,  0, 6, 0},

      {8, 0, 0,  0, 6, 0,  0, 0, 3},
      {4, 0, 0,  8, 5, 3,  0, 0, 1},
      {7, 0, 0,  0, 2, 0,  0, 0, 6},

      {0, 6, 0,  0, 0, 0,  2, 8, 0},
      {0, 0, 0,  4, 1, 9,  0, 0, 5},
      {0, 0, 0,  0, 8, 0,  0, 7, 9}
    });
  }

  @Test
  public void testSolvedValid() {
    new Game(new int[][] {
      {5, 3, 4,  6, 7, 8,  9, 1, 2},
      {6, 7, 2,  1, 9, 5,  3, 4, 8},
      {1, 9, 8,  3, 4, 2,  5, 6, 7},

      {8, 5, 9,  7, 6, 1,  4, 2, 3},
      {4, 2, 6,  8, 5, 3,  7, 9, 1},
      {7, 1, 3,  9, 2, 4,  8, 5, 6},

      {9, 6, 1,  5, 3, 7,  2, 8, 4},
      {2, 8, 7,  4, 1, 9,  6, 3, 5},
      {3, 4, 5,  2, 8, 6,  1, 7, 9}
    });
  }

  @Test
  public void testRowWrong() {
    try {
      new Game(new int[][] { // ---v
        {5, 3, 0,  0, 7, 0,  0, 0, 7},
        {6, 0, 0,  1, 9, 5,  0, 0, 0},
        {0, 9, 8,  0, 0, 0,  0, 6, 0},

        {8, 0, 0,  0, 6, 0,  0, 0, 3},
        {4, 0, 0,  8, 5, 3,  0, 0, 1},
        {7, 0, 0,  0, 2, 0,  0, 0, 6},

        {0, 6, 0,  0, 0, 0,  2, 8, 0},
        {0, 0, 0,  4, 1, 9,  0, 0, 5},
        {0, 0, 0,  0, 8, 0,  0, 7, 9}
      }).validate();
      fail("missing ValidationException to be thrown");
    } catch (ValidationException ve) {
      assertEquals(1, ve.getConstraints().size());
    }
  }

  @Test
  public void testColWrong() {
    try {
      new Game(new int[][] {
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
      }).validate();
      fail("missing ValidationException to be thrown");
    } catch (ValidationException ve) {
      assertEquals(1, ve.getConstraints().size());
    }
  }

  @Test
  public void testSquareWrong() {
    try {
      new Game(new int[][] {
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
      }).validate();
      fail("missing ValidationException to be thrown");
    } catch (ValidationException ve) {
      assertEquals(1, ve.getConstraints().size());
    }
  }

  @Test
  public void testViolateMultipleConstraints() {
    try {
      new Game(new int[][] {
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
      }).validate();
      fail("missing ValidationException to be thrown");
    } catch (ValidationException ve) {
      assertEquals(3, ve.getConstraints().size());
    }
  }

}
