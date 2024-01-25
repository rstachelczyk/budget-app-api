package com.rstachelczyk.budget.service;
import com.rstachelczyk.budget.accessor.user.UserEntity;
import com.rstachelczyk.budget.accessor.user.UserRepository;
import com.rstachelczyk.budget.dto.LoginRequest;
import com.rstachelczyk.budget.dto.LoginResponse;
import com.rstachelczyk.budget.dto.RegisterRequest;
import com.rstachelczyk.budget.security.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

  private final UserRepository userRepository;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public LoginResponse register(RegisterRequest request) {
    var user = UserEntity
      .builder()
      .firstName(request.getFirstName())
      .lastName(request.getLastName())
      .email(request.getEmail())
      .password(passwordEncoder.encode(request.getPassword()))
      .role(Role.ROLE_USER)
      .build();

    user = userService.save(user);
    var jwt = jwtService.generateToken(user);
    return LoginResponse.builder().token(jwt).build();
  }


  public LoginResponse login(LoginRequest request) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = userRepository.findByEmail(request.getEmail())
      .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
    var jwt = jwtService.generateToken(user);
    return LoginResponse.builder().token(jwt).build();
  }

}
