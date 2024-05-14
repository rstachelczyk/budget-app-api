package com.rstachelczyk.budget.dto;

import java.util.List;
import lombok.Getter;

/**
 * ErrorResponse Dto.
 */
@Getter
public class ErrorResponse {

  private final List<Error> errors;


  public ErrorResponse(final List<Error> errorList) {
    this.errors = errorList;
  }

  public ErrorResponse(final Error error) {
    this.errors = List.of(error);
  }
}
