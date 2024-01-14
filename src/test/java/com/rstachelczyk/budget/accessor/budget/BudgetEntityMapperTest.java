package com.rstachelczyk.budget.accessor.budget;

import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;

import com.rstachelczyk.budget.dto.Budget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BudgetEntityMapperTest {

  private BudgetEntityMapper mapper;
  private BudgetEntity entity;

  @BeforeEach
  void setUp() {
    mapper = new BudgetEntityMapper();
    entity = new BudgetEntity();
  }

  @Test
  @DisplayName("Id from entity is echoed on DTO")
  void idFromEntityIsEchoedOnDto() {
    entity.setId(123L);

    Budget budget = this.mapper.map(entity);

    assertThat(budget.getId()).isEqualTo(entity.getId());
  }

  @Test
  @DisplayName("Name from entity is echoed on DTO")
  void nameFromEntityIsEchoedOnDto() {
    entity.setName("Test Name");

    Budget budget = this.mapper.map(entity);

    assertThat(budget.getName()).isEqualTo(entity.getName());
  }

  @Test
  @DisplayName("targetAmount from entity is echoed on DTO")
  void targetAmountFromEntityIsEchoedOnDto() {
    entity.setTargetAmount(123L);

    Budget budget = this.mapper.map(entity);

    assertThat(budget.getTargetAmount()).isEqualTo(entity.getTargetAmount());
  }

  @Test
  @DisplayName("Icon from entity is echoed on DTO")
  void iconFromEntityIsEchoedOnDto() {
    entity.setIcon("Test Icon");

    Budget budget = this.mapper.map(entity);

    assertThat(budget.getIcon()).isEqualTo(entity.getIcon());
  }

  @Test
  @DisplayName("createdAt from entity is echoed on DTO")
  void createdAtFromEntityIsEchoedOnDto() {
    entity.setCreatedAt(now());

    Budget budget = this.mapper.map(entity);

    assertThat(budget.getCreatedAt()).isEqualTo(entity.getCreatedAt());
  }

  @Test
  @DisplayName("updatedAt from entity is echoed on DTO")
  void updatedAtFromEntityIsEchoedOnDto() {
    entity.setUpdatedAt(now());

    Budget budget = this.mapper.map(entity);

    assertThat(budget.getUpdatedAt()).isEqualTo(entity.getUpdatedAt());
  }
}
