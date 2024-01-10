package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import com.rstachelczyk.budget.exception.TransactionNotFoundException;
import com.rstachelczyk.budget.dto.Transaction;
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

  @Autowired
  public TransactionAccessor(
      TransactionRepository transactionRepository,
      TransactionEntityMapper transactionEntityMapper
  ) {
    this.transactionRepository = transactionRepository;
    this.transactionEntityMapper = transactionEntityMapper;
  }

  /**
   * Fetch transactions using pagination params and map each to DTO.
   *
   * @param pageable pagination params
   * @return page of transaction DTOs
   */
  public Page<Transaction> fetchTransactions(Pageable pageable) {
    Page<TransactionEntity> transactions = this.transactionRepository.findAll(pageable);

    return transactions.map(this.transactionEntityMapper::map);
  }

  /**
   * Fetch transaction by id and put into DTO.
   *
   * @param id transaction to be fetched
   * @return fetched transaction mapped to DTO
   */
  public Transaction fetchTransaction(long id) {
    Optional<TransactionEntity> transaction = this.transactionRepository.findById(id);

    if (transaction.isEmpty()) throw new TransactionNotFoundException(id);

    return transactionEntityMapper.map(transaction.get());
  }

  public Transaction createTransaction(TransactionCreateDto params, BudgetEntity budget) {
    TransactionEntity transaction = params.toTransactionEntity();
    transaction.setBudget(budget);

    this.transactionRepository.save(transaction);

    return this.transactionEntityMapper.map(transaction);
  }
}
