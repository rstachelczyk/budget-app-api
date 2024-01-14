package com.rstachelczyk.budget.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rstachelczyk.budget.accessor.transaction.TransactionEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Transaction Create Dto.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateDto {

  @NotEmpty(message = "Description is required.")
  @Size(max = 30)
  private String description;

  @PositiveOrZero(message = "Amount must be greater than or equal to 0.")
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
