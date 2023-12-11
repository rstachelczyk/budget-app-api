package com.rstachelczyk.budget.exception;

import com.rstachelczyk.budget.model.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global Exception Handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(TransactionNotFoundException.class)
  public ResponseEntity<Error> handleTransactionNotFound(TransactionNotFoundException ex) {
    Error error = new Error("10", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
