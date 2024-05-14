package com.rstachelczyk.budget.service;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.accessor.budget.BudgetRepository;
import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import jakarta.persistence.EntityNotFoundException;
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
      final TransactionAccessor transactionAccessor,
      //BudgetService budgetService
      final BudgetRepository budgetRepository
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
  public Page<Transaction> getTransactions(
      final int page,
      final int limit,
      final String sortBy,
      final String sortDir
  ) {
    final Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
        ? Sort.by(sortBy).ascending()
        : Sort.by(sortBy).descending();

    final Pageable pageable = PageRequest.of(page, limit, sort);

    return this.transactionAccessor.fetchTransactions(pageable);
  }

  /**
   * Fetch transaction by id.
   *
   * @param id transaction id
   *
   * @return transaction DTO
   */
  public Transaction getTransaction(final long id) {
    return this.transactionAccessor.fetchTransaction(id);
  }

  /**
   * Create transaction with params.
   *
   * @param params validated body params from create request
   *
   * @return created transaction Dto
   *
   * @throws EntityNotFoundException resource not found exception
   */
  public Transaction createTransaction(final TransactionCreateDto params) {
    //Budget budget = this.budgetService.getBudget(params.getBudgetId());

    final Optional<BudgetEntity> budget = this.budgetRepository.findById(params.getBudgetId());

    if (budget.isEmpty()) {
      throw new EntityNotFoundException(
          String.format("Could not find transaction (id=%d)", params.getBudgetId())
      );
    }

    return this.transactionAccessor.createTransaction(params, budget.get());
  }

  /**
   * Delete transaction by id.
   *
   * @param id transaction id
   */
  public void deleteTransaction(final long id) {
    this.transactionAccessor.deleteTransaction(id);
  }
}
