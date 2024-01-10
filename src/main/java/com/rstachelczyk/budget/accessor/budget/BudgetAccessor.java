package com.rstachelczyk.budget.accessor.budget;

import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.exception.BudgetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BudgetAccessor {
  private final BudgetRepository budgetRepository;
  private final BudgetEntityMapper budgetEntityMapper;

  @Autowired
  public BudgetAccessor(
    BudgetRepository budgetRepository,
    BudgetEntityMapper budgetEntityMapper
  ) {
    this.budgetRepository = budgetRepository;
    this.budgetEntityMapper = budgetEntityMapper;
  }

  public Budget fetchBudget(long id) throws BudgetNotFoundException {
    Optional<BudgetEntity> budget = this.budgetRepository.findById(id);

    if (budget.isEmpty()) throw new BudgetNotFoundException(id);

    return this.budgetEntityMapper.map(budget.get());
  }
}
