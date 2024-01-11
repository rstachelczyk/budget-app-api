package com.rstachelczyk.budget.exception;

/**
 * ResourceNotFound Exception.
 */
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String message) {
    super(message);
  }
}
