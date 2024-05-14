package com.rstachelczyk.budget.exception;

import com.rstachelczyk.budget.dto.Error;
import com.rstachelczyk.budget.dto.ErrorResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Global Exception Handler.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles resource not found errors.
   *
   * @param ex ResourceNotFoundException instance
   *
   * @return 404 not found with errors list
   */
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  @ResponseBody
  public ErrorResponse handleResourceNotFound(final EntityNotFoundException ex) {
    final Error error = new Error("20", ex.getMessage());
    return new ErrorResponse(error);
  }

  /**
   * Handles validation errors.
   *
   * @param ex MethodArgumentNotValidException instance
   *
   * @return 400 bad request with errors list
   */

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseBody
  public ErrorResponse handleValidationError(final MethodArgumentNotValidException ex) {
    final List<Error> errorList = new ArrayList<>();
    ex.getFieldErrors().forEach(e -> {
      final Error error = new Error("10", e.getDefaultMessage());
      errorList.add(error);
    });

    return new ErrorResponse(errorList);
  }
}
