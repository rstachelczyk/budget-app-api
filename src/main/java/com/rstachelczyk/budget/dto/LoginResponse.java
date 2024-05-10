package com.rstachelczyk.budget.dto;

import lombok.Builder;

@Builder
public record LoginResponse(
  String token,
  String refreshToken,
  String expirationDate
) { }
