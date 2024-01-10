package com.rstachelczyk.budget.accessor.budget;

import com.rstachelczyk.budget.accessor.transaction.TransactionEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.List;

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

  private long targetAmount;

  private String icon;

  //TODO: Do I need to make it Bi-directional?
  //@OneToMany
  //private List<TransactionEntity> transactions;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}
