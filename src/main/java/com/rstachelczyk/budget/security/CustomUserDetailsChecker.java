package com.rstachelczyk.budget.security;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import java.time.LocalDateTime;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;

/**
 * Custom User Details Checker to see if account is locked or lockout duration has ended.
 */
@Component
public class CustomUserDetailsChecker implements UserDetailsChecker {
  public static final long LOCKOUT_DURATION_MINUTES = 30;

  /**
   * Check to see if user is locked or the lockout duration has ended.
   *
   * @param user the UserDetails instance whose status should be checked.
   */
  @Override
  public void check(final UserDetails user) {
    final UserEntity entity = (UserEntity) user;

    // if account is locked, check to see if the lockout window is expired
    if (!user.isAccountNonLocked() && entity.getLockedAt()
        .isAfter(LocalDateTime.now().minusMinutes(LOCKOUT_DURATION_MINUTES))) {
      throw new LockedException("User account is locked.");
    }
  }
}
