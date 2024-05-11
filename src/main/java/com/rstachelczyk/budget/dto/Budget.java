package com.rstachelczyk.budget.dto;

import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Budget Dto.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Budget {

  private Long id;

  private String name;

  private Long targetAmount;

  private String icon;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}
