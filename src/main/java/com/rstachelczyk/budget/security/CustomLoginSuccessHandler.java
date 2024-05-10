package com.rstachelczyk.budget.security;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
  private final UserService userService;

  @Autowired
  public CustomLoginSuccessHandler(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void onAuthenticationSuccess(
    HttpServletRequest request, HttpServletResponse response, Authentication authentication
  ) throws IOException, ServletException {
    UserEntity user =  (UserEntity) authentication.getPrincipal();
    if (user.getFailedAttempts() > 0) {
      userService.unlock(user.getEmail());
    }
  }
}
