package com.rstachelczyk.budget.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Transaction DTO.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

  private Long id;

  private Long budgetId;

  private String budgetName;

  private String description;

  private Long amount;

  private String type;

  private String status;

  private Boolean isRecurring;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}
