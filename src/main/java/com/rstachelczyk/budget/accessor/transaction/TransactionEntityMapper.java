package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionUpdateDto;
import org.springframework.stereotype.Component;

import static java.time.OffsetDateTime.now;

/**
 * Maps Transaction Entity to DTO.
 */
@Component
public class TransactionEntityMapper {

  /**
   * Handles the mapping.
   *
   * @param entity transaction entity to be mapped
   * @return Transaction DTO instance
   */
  public Transaction map(TransactionEntity entity) {
    return Transaction.builder()
        .id(entity.getId())
        .budgetId(entity.getBudget().getId())
        .budgetName(entity.getBudget().getName())
        .description(entity.getDescription())
        .amount(entity.getAmount())
        .status(entity.getStatus())
        .type(entity.getType())
        .isRecurring(entity.getIsRecurring())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public TransactionEntity merge(
    TransactionEntity entity,
    TransactionUpdateDto updateParams
  ) {
    if (updateParams.getDescription() != null) {
      entity.setDescription(updateParams.getDescription());
    }

    if (updateParams.getAmount() != null) {
      entity.setAmount(updateParams.getAmount());
    }

    if (updateParams.getType() != null) {
      entity.setType(updateParams.getType());
    }

    if (updateParams.getStatus() != null) {
      entity.setStatus(updateParams.getStatus());
    }

    if (updateParams.getIsRecurring() != null) {
      entity.setIsRecurring(updateParams.getIsRecurring());
    }

    entity.setUpdatedAt(now());
    return entity;
  }
}
