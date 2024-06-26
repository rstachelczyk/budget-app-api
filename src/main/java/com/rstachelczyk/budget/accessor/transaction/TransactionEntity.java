package com.rstachelczyk.budget.accessor.transaction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

  private Long amount;

  private String type;

  private String status;

  private Boolean isRecurring;

  private OffsetDateTime createdAt;

  private OffsetDateTime updatedAt;
}
