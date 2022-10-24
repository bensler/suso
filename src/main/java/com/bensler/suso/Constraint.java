package com.bensler.suso;

import java.util.HashSet;
import java.util.Set;

import com.bensler.suso.Field.Digit;

public class Constraint {

  private final int x, y, w, h;

  public Constraint(int pX, int pY, int pW, int pH) {
    x = pX;
    y = pY;
    w = pW;
    h = pH;
  }

  /** @return if constraint is fulfilled */
  public boolean check(Field field) {
    final Set<Digit> allDigits = new HashSet<>(Digit.VALUES);

    for (int vx = x; vx < (x + w); vx++) {
      for (int vy = y; vy < (y + h); vy++) {
        final Digit digit = field.get(vx, vy);

        if ((digit != null) && (!allDigits.remove(digit))) {
          return false;
        }
      }
    };
    return true;
  }

}
