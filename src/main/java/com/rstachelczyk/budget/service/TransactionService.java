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

  /**
   * Fetch page of transactions.
   *
   * @param page page number
   * @param limit page limit
   * @param sortBy field to sort by
   * @param sortDir sort direction (desc, asc)
   * @return page of transaction DTO
   */
  public Page<Transaction> getTransactions(int page, int limit, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
        : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);

    return this.transactionAccessor.fetchTransactions(pageable);
  }

  /**
   * Fetch transaction by id.
   *
   * @param id transaction id
   * @return transaction DTO
   */
  public Transaction getTransaction(long id) {
    return this.transactionAccessor.fetchTransaction(id);
  }
}
