package com.rstachelczyk.budget.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rstachelczyk.budget.accessor.transaction.TransactionEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.OffsetDateTime;

/**
 * Transaction Create Dto.
 */
@Builder
public record TransactionCreateDto(

  @NotEmpty(message = "Description is required.") @Size(max = 30) String description,

  @PositiveOrZero(message = "Amount must be greater than or equal to 0.") Long amount,

  @Positive(message = "Budget Id must be greater than 0.")
  @NotNull(message = "Budget Id is required.")
  Long budgetId,

  @NotEmpty(message = "Type is required.") String type,

  @NotEmpty(message = "Status is required.") String status,

   boolean isRecurring
) {


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
      .build();
  }
}
