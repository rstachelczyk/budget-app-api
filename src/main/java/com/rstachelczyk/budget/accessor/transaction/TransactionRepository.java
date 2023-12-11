package com.rstachelczyk.budget.accessor.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Transaction Repository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
}
