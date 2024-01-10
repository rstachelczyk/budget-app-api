package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.budget.BudgetAccessor;
import com.rstachelczyk.budget.dto.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Budget Service.
 */
@Service
public class BudgetService {
  private final BudgetAccessor budgetAccessor;

  @Autowired
  public BudgetService(BudgetAccessor budgetAccessor) {
    this.budgetAccessor = budgetAccessor;
  }

  public Budget getBudget(long id) {
    return this.budgetAccessor.fetchBudget(id);
  }
}
