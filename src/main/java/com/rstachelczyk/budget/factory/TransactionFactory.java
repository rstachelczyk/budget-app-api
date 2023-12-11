package com.rstachelczyk.budget.factory;


import com.rstachelczyk.budget.model.Transaction;

import java.time.OffsetDateTime;

public class TransactionFactory {
    public static Transaction exampleTransaction(Long id) {
        return Transaction.builder()
                .id(id)
                .description("Test description")
                .amount(1000)
                .createdAt(OffsetDateTime.now())
                .build();
    }
}
