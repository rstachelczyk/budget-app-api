package com.rstachelczyk.budget.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rstachelczyk.budget.accessor.transaction.TransactionEntity;
import jakarta.validation.constraints.*;

import java.time.OffsetDateTime;
import lombok.Data;

/**
 * Transaction Create Dto.
 */
@Data
public class TransactionCreateDto {

  @NotEmpty(message = "Description is required.")
  @Size(max = 30)
  private String description;

  @PositiveOrZero(message = "Amount must be greater than or equal to 0.")
//  @Max(value = , message = "Amount must be less than ")
  private Long amount;

  @Positive(message = "Budget Id must be greater than 0.")
  @NotNull(message = "Budget Id is required.")
  private Long budgetId;

  @NotEmpty(message = "Type is required.")
  private String type;

  @NotEmpty(message = "Status is required.")
  private String status;

  private boolean isRecurring;

  @JsonIgnore
  private OffsetDateTime createdAt = OffsetDateTime.now();

  @JsonIgnore
  private OffsetDateTime updatedAt = OffsetDateTime.now();

  /**
   * Convert Dto to entity object.
   *
   * @return TransactionEntity instance
   */
  public TransactionEntity toTransactionEntity() {
    return TransactionEntity.builder()
      .description(this.description)
      .amount(this.amount)
      .type(this.type)
      .status(this.status)
      .isRecurring(this.isRecurring)
      .createdAt(this.createdAt)
      .updatedAt(this.updatedAt)
      .build();
  }
}
