package com.rstachelczyk.budget.security;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Custom Authentication Provider to provide account locking logic.
 */
@Component
public class CustomAuthenticationProvider extends DaoAuthenticationProvider {

  private final UserService userService;

  @Autowired
  /* default */ public CustomAuthenticationProvider(
      final UserService userService,
      final PasswordEncoder passwordEncoder,
      final CustomUserDetailsChecker customUserDetailsChecker
  ) {
    super(passwordEncoder);
    super.setUserDetailsService(userService.userDetailsService());
    super.setPreAuthenticationChecks(customUserDetailsChecker);
    this.userService = userService;
  }

  @Override
  protected void additionalAuthenticationChecks(
      final UserDetails userDetails,
      final UsernamePasswordAuthenticationToken authentication
  ) {
    if (authentication.getCredentials() == null) {
      this.logger.debug("Failed to authenticate since no credentials provided");
      throw new BadCredentialsException(this.messages
          .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
              "Bad credentials"));
    }

    final UserEntity user = (UserEntity) userDetails;
    final String presentedPassword = authentication.getCredentials().toString();

    if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
      this.updateUserLockStatus(user);
      this.logger.debug("Failed to authenticate since password does not match stored value");
      throw new BadCredentialsException(this.messages
          .getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials",
              "Bad credentials"));
    }
  }

  @Override
  protected Authentication createSuccessAuthentication(
      final Object principal,
      final Authentication authentication,
      final UserDetails user
  ) {
    final UserEntity userEntity = (UserEntity) user;
    if (userEntity.getFailedAttempts() > 0) {
      userEntity.setFailedAttempts(0);
      userEntity.setLocked(false);
      userEntity.setLockedAt(null);
      this.userService.save(userEntity);
    }

    return super.createSuccessAuthentication(principal, authentication, user);
  }

  private void updateUserLockStatus(final UserEntity user) {
    if (user.getFailedAttempts() < UserService.MAX_FAILED_ATTEMPTS - 1) {
      this.userService.increaseFailedAttempts(user);
    } else {
      this.userService.lock(user);
    }
  }
}
