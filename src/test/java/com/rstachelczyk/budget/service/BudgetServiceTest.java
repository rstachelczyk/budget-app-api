package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.accessor.budget.BudgetAccessor;
import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.dto.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BudgetServiceTest {

  @Mock
  private BudgetAccessor budgetAccessorMock;

  @InjectMocks
  private BudgetService budgetService;

  @Test
  @DisplayName("When fetching a budget by Id, returns Budget Dto")
  void givenValidBudgetId_whenGetBudget_thenReturnsBudgetDto() {
    when(budgetAccessorMock.fetchBudget(anyLong()))
      .thenReturn(new Budget());

    Budget response = this.budgetService.getBudget(TestConstants.LONG);

    assertThat(response).isNotNull();
  }
}
