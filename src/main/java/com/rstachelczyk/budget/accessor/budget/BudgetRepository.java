package com.rstachelczyk.budget.accessor.budget;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Budget Repository.
 */
@Repository
public interface BudgetRepository extends JpaRepository<BudgetEntity, Long> {
}
