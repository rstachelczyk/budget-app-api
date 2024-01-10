package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.dto.Transaction;
import org.springframework.stereotype.Component;

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
        .description(entity.getDescription())
        .amount(entity.getAmount())
        .status(entity.getStatus())
        .type(entity.getType())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }
}
