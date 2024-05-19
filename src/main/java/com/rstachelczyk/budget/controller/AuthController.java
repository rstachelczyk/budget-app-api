package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.dto.Error;
import com.rstachelczyk.budget.dto.ErrorResponse;
import com.rstachelczyk.budget.dto.LoginRequest;
import com.rstachelczyk.budget.dto.LoginResponse;
import com.rstachelczyk.budget.dto.RegisterRequest;
import com.rstachelczyk.budget.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {
  private final AuthenticationService authenticationService;
  public AuthController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/register")
  public ResponseEntity<LoginResponse> register(
    @Valid @RequestBody RegisterRequest request
  ) {
    return ResponseEntity.ok(
      this.authenticationService.register(request)
    );
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(
    @RequestBody LoginRequest request
  ) {
    return ResponseEntity.ok(
      this.authenticationService.login(request)
    );
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  @ExceptionHandler(value = LockedException.class)
  public ErrorResponse handleAuthenticationExceptions(LockedException ex) {
    log.info("Account Locked Exception: {}", ex.getMessage());
    final Error error = new Error(
      "403",
      "User account is locked. Please try again later or contact support. If you suspect "
          + "someone trying to hack your account, please reset your password immediately."
    );
    return new ErrorResponse(error);
  }

  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  @ExceptionHandler(value = BadCredentialsException.class)
  public ErrorResponse badCredentialsException(BadCredentialsException ex) {
    log.info("Bad Credentials Exception: {}", ex.getMessage());
    final Error error = new Error("403", ex.getMessage());
    return new ErrorResponse(error);
  }
}
