package com.rstachelczyk.budget.accessor.transaction;

import static org.assertj.core.api.Assertions.assertThat;

import com.rstachelczyk.budget.model.Transaction;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TransactionEntityMapperTest {

  private TransactionEntityMapper mapper;
  private TransactionEntity entity;

  @BeforeEach
  void setUp() {
    mapper = new TransactionEntityMapper();
    entity = new TransactionEntity();
  }

  @Test
  @DisplayName("Id from entity is echoed on DTO")
  void idFromEntityIsEchoedOnDto() {
    entity.setId(123L);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getId()).isEqualTo(entity.getId());
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
    entity.setAmount(1000);

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getAmount()).isEqualTo(entity.getAmount());
  }

  @Test
  @DisplayName("CreatedAt from entity is echoed on DTO")
  void createdAtFromEntityIsEchoedOnDto() {
    entity.setCreatedAt(OffsetDateTime.now());

    Transaction transaction = this.mapper.map(entity);

    assertThat(transaction.getCreatedAt()).isEqualTo(entity.getCreatedAt());
  }
}