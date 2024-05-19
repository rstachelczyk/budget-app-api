package com.rstachelczyk.budget.accessor.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);

  @Query("UPDATE UserEntity u SET u.failedAttempts = ?1 WHERE u.email = ?2")
  @Modifying
  void updateFailedAttempts(int failAttempts, String email);
}
