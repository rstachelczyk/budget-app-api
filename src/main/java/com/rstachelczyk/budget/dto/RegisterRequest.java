package com.rstachelczyk.budget.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

/**
 * Request to register new user dto.
 *
 * @param firstName first name of user to be registered
 * @param lastName last name of user to be registered
 * @param email email of user to be registered
 * @param password password of user account
 * @param confirmPassword confirm above password (should be equal)
 */
@Builder
public record RegisterRequest(

    @NotEmpty String firstName,

    @NotEmpty String lastName,

    @Email(regexp = "[^@]+@[^@]+\\.[^@.]+") @NotEmpty String email,

    @NotEmpty String password,

    @NotEmpty String confirmPassword
) { }
