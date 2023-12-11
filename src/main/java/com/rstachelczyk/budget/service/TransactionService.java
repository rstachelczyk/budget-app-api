package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Transaction Service.
 */
@Service
public class TransactionService {
  private final TransactionAccessor transactionAccessor;

  @Autowired
  public TransactionService(TransactionAccessor transactionAccessor) {
    this.transactionAccessor = transactionAccessor;
  }

  public Transaction getTransaction(long id) {
    return this.transactionAccessor.fetchTransaction(id);
  }
}
