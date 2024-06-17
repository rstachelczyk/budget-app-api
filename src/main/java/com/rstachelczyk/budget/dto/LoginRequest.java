package com.rstachelczyk.budget.dto;

/**
 * Login request Dto.
 *
 * @param email email of account that is requesting to be logged in
 * @param password password of account that is requesting to be logged in
 */
public record LoginRequest(String email, String password) { }
