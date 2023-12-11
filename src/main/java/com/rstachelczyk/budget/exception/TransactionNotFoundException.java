package com.rstachelczyk.budget.exception;

/**
 * TransactionNotFound Exception.
 */
public class TransactionNotFoundException extends RuntimeException {

  public TransactionNotFoundException(Long transactionId) {
    super("Transaction not found with ID: " + transactionId);
  }
}
