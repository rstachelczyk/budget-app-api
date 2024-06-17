package com.rstachelczyk.budget.interceptor;

import com.rstachelczyk.budget.config.SecurityConfig;
import com.rstachelczyk.budget.service.JwtService;
import com.rstachelczyk.budget.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Jwt Authentication Filter in Spring Security Filter Chain.
 */
@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private static final String AUTHORIZATION = "Authorization";
  public static final String BEARER_TOKEN_PREFIX = "Bearer ";
  private static final int BEARER_TOKEN_PREFIX_LENGTH = BEARER_TOKEN_PREFIX.length();

  private final JwtService jwtService;
  private final UserService userService;

  @Autowired
  /* default */ public JwtAuthenticationFilter(
      final JwtService jwtService,
      final UserService userService
  ) {
    super();
    this.jwtService = jwtService;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      @NonNull final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain
  ) throws ServletException, IOException {
    final String requestUri = request.getRequestURI();

    // Check if the request URI matches any of the whitelisted URLs
    boolean isWhitelisted = false;
    for (final String url : SecurityConfig.WHITE_LISTED_URLS) {
      if (requestUri.startsWith(url)) {
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

    // If the auth header is empty or does not start with "Bearer", continue filter chain processing
    if (StringUtils.isEmpty(authHeader)
        || !StringUtils.startsWith(authHeader, BEARER_TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }

    //Get JWT from auth header
    final String jwt = authHeader.substring(BEARER_TOKEN_PREFIX_LENGTH);
    log.info("JWT - {}", jwt);

    //Extract username from JWT
    final String userEmail = this.jwtService.extractUserName(jwt);

    //Check that JWT subject is present & current security context does not have user added already
    if (StringUtils.isNotEmpty(userEmail)
        && SecurityContextHolder.getContext().getAuthentication() == null) {

      //Lookup user by email in the database
      final UserDetails userDetails = userService.userDetailsService()
          .loadUserByUsername(userEmail);

      //Check to ensure the username from token & db match & token is not expired
      if (jwtService.isValidToken(jwt, userDetails)) {
        log.info("User - {}", userDetails);

        //Create new User token with userDetails and role
        final UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
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
