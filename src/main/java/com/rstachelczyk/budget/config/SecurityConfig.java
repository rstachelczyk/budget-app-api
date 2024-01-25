package com.rstachelczyk.budget.config;

import com.rstachelczyk.budget.interceptor.JwtAuthenticationFilter;
import com.rstachelczyk.budget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  // Exclude login, register, and actuator endpoints from authentication
  private static final String[] WHITE_LIST_URL = {
    "/api/v1/login",
    "/api/v1/register",
    "/actuator/**"
  };

  @Autowired
  public SecurityConfig(
    JwtAuthenticationFilter jwtAuthenticationFilter,
    UserService userService,
    PasswordEncoder passwordEncoder
  ) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(this.userService.userDetailsService());
    authProvider.setPasswordEncoder(this.passwordEncoder);
    return authProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authConfig
  ) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
      .csrf(AbstractHttpConfigurer::disable)
      .sessionManagement(session -> session
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(WHITE_LIST_URL).permitAll()
        .anyRequest().authenticated()
      )
      .authenticationProvider(this.authenticationProvider())
      .addFilterBefore(
        this.jwtAuthenticationFilter,
        UsernamePasswordAuthenticationFilter.class
      );
    //TODO: Add logout handler

    return httpSecurity.build();
  }
}
