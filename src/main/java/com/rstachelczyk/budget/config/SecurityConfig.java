package com.rstachelczyk.budget.config;

import com.rstachelczyk.budget.interceptor.JwtAuthenticationFilter;
import com.rstachelczyk.budget.security.CustomLoginFailureHandler;
import com.rstachelczyk.budget.security.CustomLoginSuccessHandler;
import com.rstachelczyk.budget.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig
  extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserService userService;
  private final PasswordEncoder passwordEncoder;
  private final CustomLoginSuccessHandler loginSuccessHandler;
  private final CustomLoginFailureHandler loginFailureHandler;

  // Exclude login, register, and actuator endpoints from authentication
  private static final String[] WHITE_LIST_URL = {
    "/api/v1/login",
    "/api/v1/register",
    "/actuator/**",
    "/api/v1/transaction"
  };

  @Autowired
  public SecurityConfig(
    JwtAuthenticationFilter jwtAuthenticationFilter,
    UserService userService,
    PasswordEncoder passwordEncoder,
    CustomLoginSuccessHandler customLoginSuccessHandler,
    CustomLoginFailureHandler customLoginFailureHandler
  ) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.loginSuccessHandler = customLoginSuccessHandler;
    this.loginFailureHandler = customLoginFailureHandler;
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
      )
      .formLogin(form -> form
        .loginProcessingUrl("/api/v1/login") // Define login URL for form-based login
        .successHandler(this.loginSuccessHandler)
        .failureHandler(this.loginFailureHandler)
      );
    //TODO: Add logout handler?

    return httpSecurity.build();
  }

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//    http
//      .csrf().disable()
//      .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//      .and()
//      .authorizeRequests()
//      .antMatchers(WHITE_LIST_URL).permitAll()
//      .anyRequest().authenticated()
//      .and()
//      .authenticationProvider(authenticationProvider())
//      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
//      .formLogin().loginProcessingUrl("/api/v1/login") // Define login URL for form-based login
//      .successHandler(loginSuccessHandler())
//      .failureHandler(loginFailureHandler());
//  }
//
}
