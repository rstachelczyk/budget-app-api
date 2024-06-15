package com.rstachelczyk.budget.interceptor;

import com.rstachelczyk.budget.config.SecurityConfig;
import com.rstachelczyk.budget.service.JwtService;
import com.rstachelczyk.budget.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.rstachelczyk.budget.config.SecurityConfig.WHITE_LIST_URL;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION = "Authorization";

  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  private static final int BEARER_TOKEN_PREFIX_LENGTH = BEARER_TOKEN_PREFIX.length();

  private final JwtService jwtService;
  private final UserService userService;

  @Autowired
  public JwtAuthenticationFilter(
    JwtService jwtService,
    UserService userService
  ) {
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain
  ) throws ServletException, IOException {
    final String requestURI = request.getRequestURI();

    // Check if the request URI matches any of the whitelisted URLs
    boolean isWhitelisted = false;
    for (String url : SecurityConfig.WHITE_LIST_URL) {
      if (requestURI.startsWith(url)) {
        isWhitelisted = true;
        break;
      }
    }

    // If the request is whitelisted, continue the filter chain without authentication
    if (isWhitelisted) {
      filterChain.doFilter(request, response);
      return;
    }

    final String authHeader = request.getHeader(AUTHORIZATION);
    final String jwt;
    final String userEmail;

    if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, BEARER_TOKEN_PREFIX)){
      filterChain.doFilter(request, response);
      return;
    }

    //Get JWT from auth header
    jwt = authHeader.substring(BEARER_TOKEN_PREFIX_LENGTH);
    log.info("JWT - {}", jwt);

    //Extract username from JWT
    userEmail = this.jwtService.extractUserName(jwt);

    //Check that JWT subject is not empty & current security context does not have user added already
    if (StringUtils.isNotEmpty(userEmail) && SecurityContextHolder.getContext().getAuthentication() == null) {

      //Lookup user by email in the database
      UserDetails userDetails = userService.userDetailsService().loadUserByUsername(userEmail);

      //Check to ensure the username from token & db match & token is not expired
      if (jwtService.isValidToken(jwt, userDetails)) {
        log.info("User - {}", userDetails);

        //Create new User token with userDetails and role
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
          userDetails, null, userDetails.getAuthorities()
        );
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        //Update security context holder to include proper user auth
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }
    }

    //Pass down the filter chain and continue processing request
    filterChain.doFilter(request, response);
  }
}
