package com.rstachelczyk.budget.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * JWT Service.
 */
@Service
@Slf4j
public class JwtService {

  private @Value("${token.secret.key}") String jwtSecretKey;
  private @Value("${token.expiration}") Long jwtExpirationTime;

  /**
   * Extract username from existing JWT.
   *
   * @param token existing token
   *
   * @return username associated with token
   */
  public String extractUserName(final String token) {
    return extractClaim(token, Claims::getSubject);
  }

  /**
   * Validate existing JWT.
   *
   * @param token token to validate
   * @param userDetails user details
   *
   * @return true if valid JWT
   */
  public boolean isValidToken(final String token, final UserDetails userDetails) {
    final String userName = extractUserName(token);
    return userName.equals(userDetails.getUsername()) && !isExpiredToken(token);
  }

  /**
   * Create new JWT token.
   *
   * @param userDetails user details to use for new token
   *
   * @return new JWT
   */
  public String generateToken(final UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  private String generateToken(
      final Map<String, Object> extraClaims,
      final UserDetails userDetails
  ) {
    return Jwts.builder()
      .claims(extraClaims)
      .subject(userDetails.getUsername())
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + this.jwtExpirationTime))
      .signWith(this.getSigningKey())
      .compact();
  }

  private <T> T extractClaim(final String token, final Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private boolean isExpiredToken(final String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(final String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(final String token) {
    return Jwts
      .parser()
      .verifyWith(this.getSigningKey())
      .build()
      .parse(token).accept(Jws.CLAIMS)
      .getPayload();
  }

  private SecretKey getSigningKey() {
    final byte[] keyBytes = Decoders.BASE64.decode(this.jwtSecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
