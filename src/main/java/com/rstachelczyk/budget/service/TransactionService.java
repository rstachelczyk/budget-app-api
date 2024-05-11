package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.accessor.budget.BudgetRepository;
import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import com.rstachelczyk.budget.exception.ResourceNotFoundException;
import java.util.Optional;
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

  //private final BudgetService budgetService;
  private final BudgetRepository budgetRepository;

  @Autowired
  public TransactionService(
      TransactionAccessor transactionAccessor,
      //BudgetService budgetService
      BudgetRepository budgetRepository
  ) {
    this.transactionAccessor = transactionAccessor;
    //this.budgetService = budgetService;
    this.budgetRepository = budgetRepository;
  }

  /**
   * Fetch page of transactions.
   *
   * @param page    page number
   * @param limit   page limit
   * @param sortBy  field to sort by
   * @param sortDir sort direction (desc, asc)
   *
   * @return page of transaction DTO
   */
  public Page<Transaction> getTransactions(int page, int limit, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
        ? Sort.by(sortBy).ascending()
        : Sort.by(sortBy).descending();

    Pageable pageable = PageRequest.of(page, limit, sort);

    return this.transactionAccessor.fetchTransactions(pageable);
  }

  /**
   * Fetch transaction by id.
   *
   * @param id transaction id
   *
   * @return transaction DTO
   */
  public Transaction getTransaction(long id) {
    return this.transactionAccessor.fetchTransaction(id);
  }

  /**
   * Create transaction with params.
   *
   * @param params validated body params from create request
   *
   * @return created transaction Dto
   *
   * @throws ResourceNotFoundException resource not found exception
   */
  public Transaction createTransaction(TransactionCreateDto params)
      throws ResourceNotFoundException {
    //Budget budget = this.budgetService.getBudget(params.getBudgetId());

    Optional<BudgetEntity> budget = this.budgetRepository.findById(params.budgetId());

    if (budget.isEmpty()) {
      throw new ResourceNotFoundException("Budget not found with Id: " + params.budgetId());
    }

    return this.transactionAccessor.createTransaction(params, budget.get());
  }

  /**
   * Delete transaction by id.
   *
   * @param id transaction id
   */
  public void deleteTransaction(long id) {
    this.transactionAccessor.deleteTransaction(id);
  }
}
