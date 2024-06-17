package com.rstachelczyk.budget.security;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Security Auditor.
 */
@Component
public class SecurityAuditor implements AuditorAware<UserEntity> {

  @Override
  public Optional<UserEntity> getCurrentAuditor() {
    return Optional.ofNullable(SecurityContextHolder.getContext())
        .map(SecurityContext::getAuthentication)
        .filter(Authentication::isAuthenticated)
        .map(Authentication::getPrincipal)
        .map(UserEntity.class::cast);
  }
}
