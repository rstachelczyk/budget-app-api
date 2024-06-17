package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.dto.Error;
import com.rstachelczyk.budget.dto.ErrorResponse;
import com.rstachelczyk.budget.dto.LoginRequest;
import com.rstachelczyk.budget.dto.LoginResponse;
import com.rstachelczyk.budget.dto.RegisterRequest;
import com.rstachelczyk.budget.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Auth REST controller.
 */
@RestController
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {

  private final AuthenticationService authenticationService;

  @Autowired
  /* default */public AuthController(final AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  /**
   * Register.
   *
   * @param request new user data
   *
   * @return authenticated JWT & refresh token
   */
  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(
      @Valid @RequestBody final RegisterRequest request
  ) {
    return ResponseEntity.ok(
        this.authenticationService.register(request)
    );
  }

  /**
   * User login.
   *
   * @param request login request object
   *
   * @return valid JWT
   */
  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
      @RequestBody final LoginRequest request
  ) {
    return ResponseEntity.ok(
        this.authenticationService.login(request)
    );
  }

  /**
   * Handle error response for when user account is locked.
   *
   * @param ex locked exception
   *
   * @return error response
   */
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  @ExceptionHandler(LockedException.class)
  public ErrorResponse handleAuthenticationExceptions(final LockedException ex) {
    log.info("Account Locked Exception: {}", ex.getMessage());
    final Error error = new Error(
        "403",
        "User account is locked. Please try again later or contact support. If you suspect "
            + "someone trying to hack your account, please reset your password immediately."
    );
    return new ErrorResponse(error);
  }

  /**
   * Handle error response when bad login attempt.
   *
   * @param ex bad credentials exception
   *
   * @return error response
   */
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  @ExceptionHandler(BadCredentialsException.class)
  public ErrorResponse badCredentialsException(final BadCredentialsException ex) {
    log.info("Bad Credentials Exception: {}", ex.getMessage());
    final Error error = new Error("403", ex.getMessage());
    return new ErrorResponse(error);
  }
}
