package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

  public Page<Transaction> getTransactions(int page, int limit, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
      : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);

    return this.transactionAccessor.fetchTransactions(pageable);
  }
  public Transaction getTransaction(long id) {
    return this.transactionAccessor.fetchTransaction(id);
  }
}
