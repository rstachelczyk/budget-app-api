package com.rstachelczyk.budget.accessor.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.dto.Transaction;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionEntityMapperTest {

  private TransactionEntityMapper mapper;
  private TransactionEntity entity;
  private BudgetEntity associatedEntity;

  @BeforeEach
  void setUp() {
    mapper = new TransactionEntityMapper();
    entity = new TransactionEntity();
    associatedEntity = new BudgetEntity();
    entity.setBudget(associatedEntity);
  }

  @Test
  @DisplayName("Id from entity is echoed on DTO")
  void idFromEntityIsEchoedOnDto() {
    entity.setId(123L);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getId()).isEqualTo(entity.getId());
  }

  @Test
  @DisplayName("budgetId from entity is echoed on DTO")
  void budgetIdFromEntityIsEchoedOnDto() {
    associatedEntity.setId(123L);
    entity.setBudget(associatedEntity);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getBudgetId()).isEqualTo(associatedEntity.getId());
  }

  @Test
  @DisplayName("budgetName from entity is echoed on DTO")
  void budgetNameFromEntityIsEchoedOnDto() {
    associatedEntity.setName("Food");
    entity.setBudget(associatedEntity);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getBudgetName()).isEqualTo(associatedEntity.getName());
  }

  @Test
  @DisplayName("Description from entity is echoed on DTO")
  void descriptionFromEntityIsEchoedOnDto() {
    entity.setDescription("Test Description");

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getDescription()).isEqualTo(entity.getDescription());
  }

  @Test
  @DisplayName("Amount from entity is echoed on DTO")
  void amountFromEntityIsEchoedOnDto() {
    entity.setAmount(1000L);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getAmount()).isEqualTo(entity.getAmount());
  }

  @Test
  @DisplayName("Status from entity is echoed on DTO")
  void statusFromEntityIsEchoedOnDto() {
    entity.setStatus("Test Status");

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getStatus()).isEqualTo(entity.getStatus());
  }

  @Test
  @DisplayName("Type from entity is echoed on DTO")
  void typeFromEntityIsEchoedOnDto() {
    entity.setType("Charge");

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getType()).isEqualTo(entity.getType());
  }

  @Test
  @DisplayName("isRecurring from entity is echoed on DTO")
  void isRecurringFromEntityIsEchoedOnDto() {
    entity.setIsRecurring(true);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getIsRecurring())
        .isEqualTo(entity.getIsRecurring());
  }

  @Test
  @DisplayName("CreatedAt from entity is echoed on DTO")
  void createdAtFromEntityIsEchoedOnDto() {
    entity.setCreatedAt(OffsetDateTime.now());

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getCreatedAt()).isEqualTo(entity.getCreatedAt());
  }

  @Test
  @DisplayName("updatedAt from entity is echoed on DTO")
  void updateAtFromEntityIsEchoedOnDto() {
    entity.setUpdatedAt(OffsetDateTime.now());

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getUpdatedAt()).isEqualTo(entity.getUpdatedAt());
  }
}
