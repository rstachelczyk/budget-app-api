package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.accessor.user.UserRepository;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

  public static final int MAX_FAILED_ATTEMPTS = 5;

  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
      this.userRepository = userRepository;
  }

  public UserDetailsService userDetailsService() {
    return username -> userRepository.findByEmail(username)
      .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  public void increaseFailedAttempts(UserEntity user) {
    int newFailAttempts = user.getFailedAttempts() + 1;
    this.userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
  }

  public void lock(UserEntity user) {
    user.setLocked(true);
    user.setLockedAt(LocalDateTime.now());

    this.userRepository.save(user);
  }

  public UserEntity save(UserEntity newUser) {
    if (newUser.getId() == null) {
      //TODO: Fix audit setting the created at date
      newUser.setCreatedAt(LocalDateTime.now());
    }

    newUser.setUpdatedAt(LocalDateTime.now());
    return userRepository.save(newUser);
  }
}
