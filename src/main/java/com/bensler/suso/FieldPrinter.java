package com.bensler.suso;

import java.util.stream.Collectors;

public class FieldPrinter {

  public static FieldPrinter INSTANCE = new FieldPrinter();

  private FieldPrinter() {}

  public String print(Field field) {
    return field.getSetCoordinates().stream()
      .sorted()
      .map(coordinate -> coordinate + ":" + field.get(coordinate).get())
      .collect(Collectors.joining("\n"));
  }

}