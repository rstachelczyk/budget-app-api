package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import com.rstachelczyk.budget.exception.ResourceNotFoundException;
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
   * @param transactionRepository transaction repository
   * @param transactionEntityMapper entity mapper used to convert to Dto
   */
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
   * @throws ResourceNotFoundException resource not found exception
   */
  public Transaction fetchTransaction(long id) throws ResourceNotFoundException {
    Optional<TransactionEntity> transaction = this.transactionRepository.findById(id);

    if (transaction.isEmpty()) throw new ResourceNotFoundException(
        "Transaction not found with Id: " + id);

    return transactionEntityMapper.map(transaction.get());
  }

  /**
   * Create transaction record and map to DTO.
   *
   * @param params TransactionCreateDto instance
   * @param budget BudgetEntity instance
   * @return Transaction Dto of persisted transaction
   */
  public Transaction createTransaction(TransactionCreateDto params, BudgetEntity budget) {
    TransactionEntity transaction = params.toTransactionEntity();
    transaction.setBudget(budget);

    this.transactionRepository.save(transaction);

    return this.transactionEntityMapper.map(transaction);
  }


  /**
   * Delete transaction by id.
   *
   * @param id transaction to be deleted
   * @throws ResourceNotFoundException resource not found exception
   */
  public void deleteTransaction(long id) throws ResourceNotFoundException {
    this.fetchTransaction(id);

    this.transactionRepository.deleteById(id);
  }
}
