package com.rstachelczyk.budget.accessor.transaction;

import com.rstachelczyk.budget.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionEntityMapper {

    public Transaction map(TransactionEntity entity) {
        return Transaction.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .amount(entity.getAmount())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
