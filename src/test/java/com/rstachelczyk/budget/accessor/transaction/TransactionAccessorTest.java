package com.rstachelczyk.budget.accessor.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.exception.TransactionNotFoundException;
import com.rstachelczyk.budget.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class TransactionAccessorTest {

    @Mock
    private TransactionRepository transactionRepositoryMock;

    @Mock
    private TransactionEntityMapper transactionEntityMapperMock;

    @InjectMocks
    private TransactionAccessor transactionAccessor;

    @Test
    @DisplayName("When Transaction record exists, finds record and maps to DTO")
    void whenGivenValidId_fetchTransaction_successfullyReturnsTransactionDTO() {
        long id = TestConstants.LONG;

        when(transactionRepositoryMock.findById(id)).thenReturn(Optional.of(new TransactionEntity()));

        when(transactionEntityMapperMock.map(any(TransactionEntity.class))).thenReturn(new Transaction());

        Transaction response = this.transactionAccessor.fetchTransaction(id);

        assertThat(response).isNotNull();
    }

    @Test
    @DisplayName("When Transaction record doesn't exist, throws TransactionNotFoundException")
    void whenGivenInvalidId_fetchTransaction_successfullyReturnsTransactionDTO() {
        long id = TestConstants.LONG;

        when(transactionRepositoryMock.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> this.transactionAccessor.fetchTransaction(id)
        );
    }
}