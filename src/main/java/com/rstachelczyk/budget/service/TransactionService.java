package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.accessor.budget.BudgetRepository;
import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.accessor.transaction.TransactionEntity;
import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import com.rstachelczyk.budget.exception.BudgetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Transaction Service.
 */
@Service
public class TransactionService {
  private final TransactionAccessor transactionAccessor;
//  private final BudgetService budgetService;
  private final BudgetRepository budgetRepository;

  @Autowired
  public TransactionService(
    TransactionAccessor transactionAccessor,
//    BudgetService budgetService
    BudgetRepository budgetRepository
  ) {
    this.transactionAccessor = transactionAccessor;
//    this.budgetService = budgetService;
    this.budgetRepository = budgetRepository;
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

  public Transaction createTransaction(TransactionCreateDto params) {
//    Budget budget = this.budgetService.getBudget(params.getBudgetId());

    Optional<BudgetEntity> budget = this.budgetRepository.findById(params.getBudgetId());

    if (budget.isEmpty()) throw new BudgetNotFoundException(params.getBudgetId());

    return this.transactionAccessor.createTransaction(params, budget.get());
  }
}
