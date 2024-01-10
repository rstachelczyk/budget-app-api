package com.rstachelczyk.budget.exception;

/**
 * BudgetNotFound Exception.
 */
public class BudgetNotFoundException extends RuntimeException {

  public BudgetNotFoundException(Long budgetId) {
    super("Budget not found with ID: " + budgetId);
  }
}
