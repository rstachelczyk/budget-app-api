package com.rstachelczyk.budget.security;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class CustomUserDetailsChecker implements UserDetailsChecker {
  public static final long LOCKOUT_DURATION_MINUTES = 30;

  public CustomUserDetailsChecker() { }

  public void check(UserDetails user) {
    UserEntity entity = (UserEntity) user;
    if (!user.isAccountNonLocked()) {
      // if account is locked, check to see if the lockout window is expired
      if (entity.getLockedAt().isAfter(LocalDateTime.now().minusMinutes(LOCKOUT_DURATION_MINUTES))) {
//        AbstractUserDetailsAuthenticationProvider.this.logger
//            .debug("Failed to authenticate since user account is locked");
//        throw new LockedException(AbstractUserDetailsAuthenticationProvider.this.messages
//            .getMessage("AbstractUserDetailsAuthenticationProvider.locked", "User account is locked"));
        throw new LockedException("User account is locked.");
      }
    }

//    if (!user.isEnabled()) {
//      AbstractUserDetailsAuthenticationProvider.this.logger
//          .debug("Failed to authenticate since user account is disabled");
//      throw new DisabledException(AbstractUserDetailsAuthenticationProvider.this.messages
//          .getMessage("AbstractUserDetailsAuthenticationProvider.disabled", "User is disabled"));
//    }
//    if (!user.isAccountNonExpired()) {
//      AbstractUserDetailsAuthenticationProvider.this.logger
//          .debug("Failed to authenticate since user account has expired");
//      throw new AccountExpiredException(AbstractUserDetailsAuthenticationProvider.this.messages
//          .getMessage("AbstractUserDetailsAuthenticationProvider.expired", "User account has expired"));
//    }
    }
}
