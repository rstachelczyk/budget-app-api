package com.rstachelczyk.budget.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Transaction Update Dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionUpdateDto {

  @Size(max = 30)
  private String description;

  @PositiveOrZero(message = "Amount must be greater than or equal to 0.")
  private Long amount;

  @Positive(message = "Budget Id must be greater than 0.")
  private Long budgetId;

  //TODO: Custom Validation that type is in enum
  private String type;

  //TODO: Custom Validation that status is in enum
  private String status;

  private Boolean isRecurring;

  @JsonIgnore
  private OffsetDateTime updatedAt = OffsetDateTime.now();
}
