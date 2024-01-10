package com.rstachelczyk.budget.accessor.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import jakarta.persistence.*;

import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

/**
 * Transaction Db Entity.
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class TransactionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //private Long userId;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "budget_id", nullable = false, referencedColumnName = "id")
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JsonIgnore
  private BudgetEntity budget;

  private String description;

  private long amount;

  private String type;

  private String status;

  private boolean isRecurring;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}
