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
  final char numberChar;
  final String numberStr;

  private Digit(int pNumber) {
    number = pNumber;
    numberStr = String.valueOf(number);
    numberChar = numberStr.charAt(0);
  }

  public int getNumber() {
    return number;
  }

  public String getNumberString() {
    return numberStr;
  }

  public char getNumberChar() {
    return numberChar;
  }

}