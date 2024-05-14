package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * Transaction Database Accessor.
 */
@Component
public class TransactionAccessor {

  private final TransactionRepository transactionRepository;

  private final TransactionEntityMapper transactionEntityMapper;

  /**
   * Constructor.
   *
   * @param transactionRepository   transaction repository
   * @param transactionEntityMapper entity mapper used to convert to Dto
   */
  @Autowired
  public TransactionAccessor(
      final TransactionRepository transactionRepository,
      final TransactionEntityMapper transactionEntityMapper
  ) {
    this.transactionRepository = transactionRepository;
    this.transactionEntityMapper = transactionEntityMapper;
  }

  /**
   * Fetch transactions using pagination params and map each to DTO.
   *
   * @param pageable pagination params
   *
   * @return page of transaction DTOs
   */
  public Page<Transaction> fetchTransactions(final Pageable pageable) {
    final Page<TransactionEntity> transactions = this.transactionRepository.findAll(pageable);

    return transactions.map(this.transactionEntityMapper::map);
  }

  /**
   * Fetch transaction by id and put into DTO.
   *
   * @param id transaction to be fetched
   *
   * @return fetched transaction mapped to DTO
   *
   * @throws EntityNotFoundException entity not found exception
   */
  public Transaction fetchTransaction(final long id) {
    final Optional<TransactionEntity> transaction = this.transactionRepository.findById(id);

    if (transaction.isEmpty()) {
      throw new EntityNotFoundException(String.format("Could not find transaction (id=%d)", id));
    }

    return transactionEntityMapper.map(transaction.get());
  }

  /**
   * Create transaction record and map to DTO.
   *
   * @param params TransactionCreateDto instance
   * @param budget BudgetEntity instance
   *
   * @return Transaction Dto of persisted transaction
   */
  public Transaction createTransaction(
      final TransactionCreateDto params,
      final BudgetEntity budget
  ) {
    final TransactionEntity transaction = params.toTransactionEntity();
    transaction.setBudget(budget);

    this.transactionRepository.save(transaction);

    return this.transactionEntityMapper.map(transaction);
  }


  /**
   * Delete transaction by id.
   *
   * @param id transaction to be deleted
   *
   * @throws EntityNotFoundException entity not found exception
   */
  public void deleteTransaction(final long id) {
    this.fetchTransaction(id);

    this.transactionRepository.deleteById(id);
  }
}
