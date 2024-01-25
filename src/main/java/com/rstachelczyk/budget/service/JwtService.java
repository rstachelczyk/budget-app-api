package com.rstachelczyk.budget.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtService {
  public static final String BEARER_TOKEN_PREFIX = "Bearer ";

  private static final int BEARER_TOKEN_PREFIX_LENGTH = BEARER_TOKEN_PREFIX.length();

  @Value("${token.secret.key}") String jwtSecretKey;
  @Value("${token.expiration}") Long jwtExpirationTime;

  public String extractUserName(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public boolean isValidToken(String token, UserDetails userDetails) {
    final String userName = extractUserName(token);
    return (userName.equals(userDetails.getUsername())) && !isExpiredToken(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return Jwts
      .builder()
      .claims(extraClaims)
      .subject(userDetails.getUsername())
      .issuedAt(new Date(System.currentTimeMillis()))
      .expiration(new Date(System.currentTimeMillis() + jwtExpirationTime))
      .signWith(this.getSigningKey(), SignatureAlgorithm.HS256)
      .compact();
  }

  private boolean isExpiredToken(String token) {
    return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts
      .parser()
      .verifyWith(this.getSigningKey())
      .build()
      .parse(token).accept(Jws.CLAIMS)
      .getPayload();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

}
