package com.rstachelczyk.budget.exception;

import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.dto.Error;
import com.rstachelczyk.budget.dto.ErrorResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Global Exception Handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles resource not found errors.
   * @param ex  ResourceNotFoundException instance
   * @return 404 not found with errors list
   */
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
    Error error = new Error("20", ex.getMessage());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(error));
  }

  /**
   * Handles validation errors.
   *
   * @param ex MethodArgumentNotValidException instance
   * @return 400 bad request with errors list
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationError(MethodArgumentNotValidException ex) {
    List<Error> errorList = new ArrayList<>();
    ex.getFieldErrors().forEach(e -> {
      Error error = new Error("10", e.getDefaultMessage());
      errorList.add(error);
    });

    ErrorResponse errorResponse = new ErrorResponse(errorList);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
  }
}
