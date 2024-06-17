package com.rstachelczyk.budget.config;

import com.rstachelczyk.budget.interceptor.JwtAuthenticationFilter;
import com.rstachelczyk.budget.security.CustomAuthenticationProvider;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security Config.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableJpaAuditing
public class SecurityConfig
    extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final CustomAuthenticationProvider customAuthenticationProvider;

  // Exclude login, register, and actuator endpoints from authentication
  private static final String[] WHITE_LIST_URL = {
      "/api/v1/login",
      "/api/v1/register",
      "/actuator/health",
      "/actuator/info",
  };

  public static final List<String> WHITE_LISTED_URLS = Collections.unmodifiableList(
      List.of(WHITE_LIST_URL)
  );

  @Autowired
  /* default */ public SecurityConfig(
      final JwtAuthenticationFilter jwtAuthenticationFilter,
      final CustomAuthenticationProvider customAuthenticationProvider
  ) {
    super();
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.customAuthenticationProvider = customAuthenticationProvider;
  }

  //TODO: Is this needed?
  @Bean
  public AuthenticationManager authenticationManager(
      final AuthenticationConfiguration authConfig
  ) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  /**
   * Build security filter chain for authentication.
   *
   * @param httpSecurity httpSecurity
   *
   * @return security filter chain
   *
   * @throws Exception e
   */
  @Bean
  public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity)
      throws Exception {

    httpSecurity
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(WHITE_LIST_URL).permitAll()
            .anyRequest().authenticated()
        )
        .authenticationProvider(this.customAuthenticationProvider)
        .addFilterBefore(
            this.jwtAuthenticationFilter,
            UsernamePasswordAuthenticationFilter.class
        );

    return httpSecurity.build();
  }
}
