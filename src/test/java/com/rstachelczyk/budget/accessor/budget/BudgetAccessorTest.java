package com.rstachelczyk.budget.accessor.budget;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.exception.ResourceNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BudgetAccessorTest {

  @Mock
  private BudgetRepository budgetRepositoryMock;

  @Mock
  private BudgetEntityMapper budgetEntityMapperMock;

  @InjectMocks
  private BudgetAccessor budgetAccessor;

  @Test
  @DisplayName("When Budget record exists, finds record and maps to DTO")
  void whenGivenValidId_fetchBudget_successfullyReturnsBudgetDto() {
    long id = TestConstants.LONG_OBJECT;

    when(budgetRepositoryMock.findById(id)).thenReturn(Optional.of(new BudgetEntity()));

    when(budgetEntityMapperMock.map(any(BudgetEntity.class)))
        .thenReturn(new Budget());

    Budget response = this.budgetAccessor.fetchBudget(id);

    assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("When Budget record doesn't exist, throws ResourceNotFoundException")
  void whenGivenInvalidId_fetchBudget_throwsResourceNotFoundException() {
    long id = TestConstants.LONG;

    when(budgetRepositoryMock.findById(id)).thenReturn(Optional.empty());

    assertThrows(
        ResourceNotFoundException.class,
        () -> this.budgetAccessor.fetchBudget(id)
    );
  }
}
