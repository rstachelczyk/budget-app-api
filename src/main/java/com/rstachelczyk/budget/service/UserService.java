package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.accessor.user.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * User service.
 */
@Service
@Transactional
public class UserService {

  public static final int MAX_FAILED_ATTEMPTS = 5;

  private final UserRepository userRepository;

  @Autowired
  /* default */ public UserService(final UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Get user details service lambda.
   *
   * @return User details service lambda for extracting users from DB.
   */
  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  /**
   * Increase failed attempts for user by 1 on bad login attempts.
   *
   * @param user user entity
   */
  public void increaseFailedAttempts(final UserEntity user) {
    final int newFailAttempts = user.getFailedAttempts() + 1;
    this.userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
  }

  /**
   * Lock user account and update lockedAt datetime.
   *
   * @param user user entity
   */
  public void lock(final UserEntity user) {
    user.setLocked(true);
    user.setLockedAt(LocalDateTime.now());

    this.userRepository.save(user);
  }

  /**
   * Set timestamps when creating or updating user. Will be removed for auditor logic.
   *
   * @param newUser user entity
   *
   * @return updated user entity after saving to db
   */
  public UserEntity save(final UserEntity newUser) {
    if (newUser.getId() == null) {
      //TODO: Fix audit setting the created at date
      newUser.setCreatedAt(LocalDateTime.now());
    }

    newUser.setUpdatedAt(LocalDateTime.now());
    return userRepository.save(newUser);
  }
}
