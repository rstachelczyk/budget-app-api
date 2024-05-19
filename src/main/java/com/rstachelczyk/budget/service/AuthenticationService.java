package com.rstachelczyk.budget.service;
import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.dto.LoginRequest;
import com.rstachelczyk.budget.dto.LoginResponse;
import com.rstachelczyk.budget.dto.RegisterRequest;
import com.rstachelczyk.budget.security.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public LoginResponse register(RegisterRequest request) {
    UserEntity user = UserEntity.builder()
      .firstName(request.firstName())
      .lastName(request.lastName())
      .email(request.email())
      .password(passwordEncoder.encode(request.password()))
      .role(Role.ROLE_USER)
      .build();

    user = userService.save(user);
    return this.generateToken(user);
  }

  public LoginResponse login(LoginRequest request) {
    Authentication auth = this.authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.email(), request.password()));
    UserEntity validUser = (UserEntity) auth.getPrincipal();
    return this.generateToken(validUser);
  }

  private LoginResponse generateToken(UserEntity user) {
    String jwt = this.jwtService.generateToken(user);
    return LoginResponse.builder().token(jwt).build();
  }
}
