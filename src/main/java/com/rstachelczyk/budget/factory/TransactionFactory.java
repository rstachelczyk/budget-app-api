package com.rstachelczyk.budget.factory;

import com.rstachelczyk.budget.dto.Transaction;
import java.time.LocalDateTime;

/**
 * Transaction Factory.
 */
public final class TransactionFactory {

  /**
   * Private Constructor.
   */
  private TransactionFactory() { }

  /**
   * Create example transaction.
   *
   * @param id transaction id
   *
   * @return transaction DTO
   */
  public static Transaction exampleTransaction(final Long id) {
    return Transaction.builder()
        .id(id)
        .description("Test description")
        .amount(1000L)
        .createdAt(LocalDateTime.now())
        .build();
  }
}
