package com.rstachelczyk.budget.accessor.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * User Repository.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByEmail(String email);

  @Query("UPDATE UserEntity u SET u.failedAttempts = ?1 WHERE u.email = ?2")
  @Modifying
  void updateFailedAttempts(int failAttempts, String email);
}
