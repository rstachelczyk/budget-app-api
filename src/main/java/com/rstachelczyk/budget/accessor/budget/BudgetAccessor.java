package com.rstachelczyk.budget.accessor.budget;

import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.exception.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Budget Database Accessor.
 */
@Component
public class BudgetAccessor {
  private final BudgetRepository budgetRepository;
  private final BudgetEntityMapper budgetEntityMapper;

  /**
   * Constructor.
   *
   * @param budgetRepository budget repository
   * @param budgetEntityMapper entity mapper used to convert to Dto
   */
  @Autowired
  public BudgetAccessor(
      BudgetRepository budgetRepository,
      BudgetEntityMapper budgetEntityMapper
  ) {
    this.budgetRepository = budgetRepository;
    this.budgetEntityMapper = budgetEntityMapper;
  }

  public List<Budget> fetchBudgets() {
    List<BudgetEntity> budgets = this.budgetRepository.findAll();

    return budgets.stream()
        .map(this.budgetEntityMapper::map)
        .collect(Collectors.toList());
  }

  /**
   * Fetch budget by id and put into Dto.
   *
   * @param id budget to be fetched
   * @return fetched budget mapped to Dto
   * @throws ResourceNotFoundException resource not found exception
   */
  public Budget fetchBudget(long id) throws ResourceNotFoundException {
    Optional<BudgetEntity> budget = this.budgetRepository.findById(id);

    if (budget.isEmpty()) throw new ResourceNotFoundException(
        "Budget not found with Id: " + id);

    return this.budgetEntityMapper.map(budget.get());
  }
}
