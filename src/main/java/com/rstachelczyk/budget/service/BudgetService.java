package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.budget.BudgetAccessor;
import com.rstachelczyk.budget.dto.Budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Budget Service.
 */
@Service
public class BudgetService {
  private final BudgetAccessor budgetAccessor;

  /**
   * Constructor.
   *
   * @param budgetAccessor budget accessor
   */
  @Autowired
  public BudgetService(BudgetAccessor budgetAccessor) {
    this.budgetAccessor = budgetAccessor;
  }

  public List<Budget> getBudgets() {
    return this.budgetAccessor.fetchBudgets();
  }
  /**
   * Fetch budget by id.
   *
   * @param id budget id
   * @return budget Dto
   */
  public Budget getBudget(long id) {
    return this.budgetAccessor.fetchBudget(id);
  }
}
