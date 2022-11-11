package com.bensler.suso;

public final class Coordinate implements Comparable<Coordinate> {

  private final int x;
  private final int y;

  public Coordinate(int pX, int pY) {
    x = pX;
    y = pY;
  }

  @Override
  public int hashCode() {
    return (10 * y) + x;
  }

  @Override
  public boolean equals(Object obj) {
    return (
      (obj != null)
      && (
        (obj == this)
        || (
          obj.getClass().equals(getClass())
          && equals((Coordinate)obj)
        )
      )
    );
  }

  public boolean equals(Coordinate other) {
    return (x == other.x) && (y == other.y);
  }

  @Override
  public int compareTo(Coordinate other) {
    final int rowCmp = y - other.y;

    return ((rowCmp != 0) ? rowCmp : (x - other.x));
  }

  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  public int getY() {
    return y;
  }

  public int getX() {
    return x;
  }

}