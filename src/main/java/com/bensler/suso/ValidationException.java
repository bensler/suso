package com.bensler.suso;

import java.util.Set;

public class ValidationException extends Exception {

  private final Set<Constraint> constraints;

  public ValidationException(Set<Constraint> pConstraint) {
    constraints = Set.copyOf(pConstraint);
  }

  public Set<Constraint> getConstraints() {
    return constraints;
  }

}
