package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.exception.TransactionNotFoundException;
import com.rstachelczyk.budget.model.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
}
