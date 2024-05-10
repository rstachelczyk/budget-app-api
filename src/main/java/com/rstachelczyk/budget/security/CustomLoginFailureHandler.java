package com.rstachelczyk.budget.security;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomLoginFailureHandler implements AuthenticationFailureHandler {

  private final UserService userService;

  public CustomLoginFailureHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationFailure(
    HttpServletRequest request, HttpServletResponse response, AuthenticationException exception
  ) throws IOException, ServletException {
    String email = request.getParameter("email");
    Optional<UserEntity> user = this.userService.getByEmail(email);
    if (user.isPresent()) {
      if(user.get().isEnabled() && user.get().isAccountNonLocked()) {
        if (user.get().getFailedAttempts() < UserService.MAX_FAILED_ATTEMPTS - 1) {
          this.userService.increaseFailedAttempts(user.get());
        } else {
          this.userService.lock(user.get());
          exception = new LockedException("Your account has been locked due to 3 failed attempts."
            + " It will be unlocked after 24 hours.");
        }
      } else if (!user.get().isAccountNonLocked()) {
        //TODO: Keep increasing lockout time
        if (this.userService.unlockWhenTimeExpired(user.get())) {
          exception = new LockedException("Your account has been unlocked. Please try to login again.");
        }
      }
    }
  }
}
