package com.rstachelczyk.budget.model;

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
  private String description;
  private long amount;
  //private Long categoryId;

  private OffsetDateTime createdAt;
}
