package com.rstachelczyk.budget.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Budget Dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {
  private long id;
  private String name;
  private long targetAmount;
  private String icon;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;
}
