package com.rstachelczyk.budget.dto;

/**
 * Error record object.
 *
 * @param code error code
 * @param message error message
 */
public record Error(String code, String message) { }
