package com.rstachelczyk.budget.accessor.budget;

import com.rstachelczyk.budget.dto.Budget;
import org.springframework.stereotype.Component;

/**
 * Maps Budget Entity to DTO.
 */
@Component
public class BudgetEntityMapper {

  /**
   * Handles the mapping.
   *
   * @param entity budget entity to be mapped
   * @return Budget Dto instance
   */
  public Budget map(BudgetEntity entity) {
    return Budget.builder()
      .id(entity.getId())
      .name(entity.getName())
      .targetAmount(entity.getTargetAmount())
      .icon(entity.getIcon())
      .createdAt(entity.getCreatedAt())
      .updatedAt(entity.getUpdatedAt())
      .build();
  }
}
