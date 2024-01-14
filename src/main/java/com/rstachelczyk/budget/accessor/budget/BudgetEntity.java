package com.rstachelczyk.budget.accessor.budget;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Budget Db Entity.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "budgets")
public class BudgetEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Long targetAmount;

  private String icon;

  //TODO: Do I need to make it Bi-directional?
  //@OneToMany
  //private List<TransactionEntity> transactions;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}
