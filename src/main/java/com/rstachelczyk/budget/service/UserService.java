package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.accessor.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class UserService {

  private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsService userDetailsService() {
      return username -> userRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserEntity save(UserEntity newUser) {
      if (newUser.getId() == null) {
        newUser.setCreatedAt(OffsetDateTime.now());
      }

      newUser.setUpdatedAt(OffsetDateTime.now());
      return userRepository.save(newUser);
    }

}
