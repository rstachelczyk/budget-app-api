package com.rstachelczyk.budget.dto;

import lombok.Builder;

/**
 * Api Response after successfully logging in.
 *
 * @param token JWT token
 * @param refreshToken refresh JWT token
 * @param expirationDate expiration date of initial token
 */
@Builder
public record LoginResponse(
    String token,
    String refreshToken,
    String expirationDate
) { }
