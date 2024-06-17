package com.rstachelczyk.budget.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.Set;


class RegisterRequestTest {

  private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
  private final Validator validator = factory.getValidator();

  @Test
  void whenAllRequestFieldsAreValid_validationConstraintsAreNull() {
    RegisterRequest request = RegisterRequest.builder()
        .firstName("Luke")
        .lastName("Skywalker")
        .email("luke@gmail.com")
        .password("password")
        .confirmPassword("password")
        .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isTrue();
  }

  @Test
  void whenFirstNameIsEmpty_validationConstraintsArePresent() {
    RegisterRequest request = RegisterRequest.builder()
      .firstName(null)
      .lastName("Skywalker")
      .email("luke@gmail.com")
      .password("password")
      .confirmPassword("password")
      .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isFalse();
    assertThat(violationSet).extracting(violation -> violation.getPropertyPath().toString())
      .containsExactlyInAnyOrderElementsOf(List.of("firstName"));
  }

  @Test
  void whenLastNameIsEmpty_validationConstraintsArePresent() {
    RegisterRequest request = RegisterRequest.builder()
      .firstName("Luke")
      .lastName(null)
      .email("luke@gmail.com")
      .password("password")
      .confirmPassword("password")
      .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isFalse();
    assertThat(violationSet).extracting(violation -> violation.getPropertyPath().toString())
      .containsExactlyInAnyOrderElementsOf(List.of("lastName"));
  }

  @Test
  void whenEmailIsEmpty_validationConstraintsArePresent() {
    RegisterRequest request = RegisterRequest.builder()
      .firstName("Luke")
      .lastName("Skywalker")
      .email(null)
      .password("password")
      .confirmPassword("password")
      .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isFalse();
    assertThat(violationSet).extracting(violation -> violation.getPropertyPath().toString())
      .containsExactlyInAnyOrderElementsOf(List.of("email"));
  }

  @ParameterizedTest
  @ValueSource(strings = { "test@com", "test.com", "test"})
  void whenEmailIsInvalid_validationConstraintsArePresent(String email) {
    RegisterRequest request = RegisterRequest.builder()
      .firstName("Luke")
      .lastName("Skywalker")
      .email(email)
      .password("password")
      .confirmPassword("password")
      .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isFalse();
    assertThat(violationSet).extracting(violation -> violation.getPropertyPath().toString())
      .containsExactlyInAnyOrderElementsOf(List.of("email"));
  }

  @Test
  void whenPasswordIsEmpty_validationConstraintsArePresent() {
    RegisterRequest request = RegisterRequest.builder()
      .firstName("Luke")
      .lastName("Skywalker")
      .email("luke@gmail.com")
      .password(null)
      .confirmPassword("password")
      .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isFalse();
    assertThat(violationSet).extracting(violation -> violation.getPropertyPath().toString())
      .containsExactlyInAnyOrderElementsOf(List.of("password"));
  }

  @Test
  void whenConfirmPasswordIsEmpty_validationConstraintsArePresent() {
    RegisterRequest request = RegisterRequest.builder()
      .firstName("Luke")
      .lastName("Skywalker")
      .email("luke@gmail.com")
      .password("password")
      .confirmPassword(null)
      .build();

    Set<ConstraintViolation<RegisterRequest>> violationSet = validator.validate(request);

    assertThat(violationSet.isEmpty()).isFalse();
    assertThat(violationSet).extracting(violation -> violation.getPropertyPath().toString())
      .containsExactlyInAnyOrderElementsOf(List.of("confirmPassword"));
  }

}
