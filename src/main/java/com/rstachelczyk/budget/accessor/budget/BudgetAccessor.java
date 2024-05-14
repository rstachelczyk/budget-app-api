package com.rstachelczyk.budget.accessor.budget;

import com.rstachelczyk.budget.dto.Budget;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
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
   * @param budgetRepository   budget repository
   * @param budgetEntityMapper entity mapper used to convert to Dto
   */
  @Autowired
  public BudgetAccessor(
      final BudgetRepository budgetRepository,
      final BudgetEntityMapper budgetEntityMapper
  ) {
    this.budgetRepository = budgetRepository;
    this.budgetEntityMapper = budgetEntityMapper;
  }

  /**
   * Fetch budget by id and put into Dto.
   *
   * @param id budget to be fetched
   *
   * @return fetched budget mapped to Dto
   *
   * @throws EntityNotFoundException entity not found exception
   */
  public Budget fetchBudget(final long id) {
    final Optional<BudgetEntity> budget = this.budgetRepository.findById(id);

    if (budget.isEmpty()) {
      throw new EntityNotFoundException(String.format("Could not find budget (id=%d)", id));
    }

    return this.budgetEntityMapper.map(budget.get());
  }
}
