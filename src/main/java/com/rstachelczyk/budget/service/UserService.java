package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.accessor.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class UserService {
  public static final int MAX_FAILED_ATTEMPTS = 5;

//  private static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours
  private static final long LOCK_TIME_DURATION = 5 * 60 * 1000; // 5 minutes

  private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService() {
      return username -> userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Optional<UserEntity> getByEmail(String email) {
      return userRepository.findByEmail(email);
    }

    public void increaseFailedAttempts(UserEntity user) {
      int newFailAttempts = user.getFailedAttempts() + 1;
      this.userRepository.updateFailedAttempts(newFailAttempts, user.getEmail());
    }

    public void unlock(String email) {
      this.userRepository.updateFailedAttempts(0, email);
      this.userRepository.removeLockedAt(email);
    }

    public void lock(UserEntity user) {
      user.setLocked(true);
      user.setLockedAt(new Date());

      this.userRepository.save(user);
    }

    public boolean unlockWhenTimeExpired(UserEntity user) {
      final long lockedAtTime = user.getLockedAt().getTime();
      final long currentTime = System.currentTimeMillis();

      if (lockedAtTime + LOCK_TIME_DURATION < currentTime) {
        user.setLocked(false);
        user.setLockedAt(null);
        user.setFailedAttempts(0);

        this.userRepository.save(user);

        return true;
      }

      return false;
    }

    public UserEntity save(UserEntity newUser) {
      if (newUser.getId() == null) {
        newUser.setCreatedAt(LocalDateTime.now());
      }

      newUser.setUpdatedAt(LocalDateTime.now());
      return userRepository.save(newUser);
    }

}
