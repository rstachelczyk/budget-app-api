package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.exception.TransactionNotFoundException;
import com.rstachelczyk.budget.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TransactionAccessor {

    private final TransactionRepository transactionRepository;
    private final TransactionEntityMapper transactionEntityMapper;

    @Autowired
    public TransactionAccessor(TransactionRepository transactionRepository, TransactionEntityMapper transactionEntityMapper) {
       this.transactionRepository = transactionRepository;
       this.transactionEntityMapper = transactionEntityMapper;
    }

    public Transaction fetchTransaction(long id) {
        Optional<TransactionEntity> transaction = this.transactionRepository.findById(id);

        if (transaction.isEmpty()) throw new TransactionNotFoundException(id);

        return transactionEntityMapper.map(transaction.get());
    }
}
