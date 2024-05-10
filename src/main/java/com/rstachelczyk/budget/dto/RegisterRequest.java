package com.rstachelczyk.budget.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
public record RegisterRequest(

  @NotEmpty String firstName,

  @NotEmpty String lastName,

  @Email(regexp = "[^@]+@[^@]+\\.[^@.]+" ) @NotEmpty String email,

  @NotEmpty String password,

  @NotEmpty String confirmPassword
) { }
