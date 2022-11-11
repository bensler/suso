package com.bensler.suso;

import java.util.Set;

public enum Digit {
  _1_(1),
  _2_(2),
  _3_(3),
  _4_(4),
  _5_(5),
  _6_(6),
  _7_(7),
  _8_(8),
  _9_(9);

  public final static Set<Digit> VALUES = Set.of(Digit.values());

  final int number;

  private Digit(int pNumber) {
    number = pNumber;
  }

  @Override
  public String toString() {
    return Integer.valueOf(number).toString();
  }

}