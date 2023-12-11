package com.rstachelczyk.budget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.model.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  private TransactionAccessor transactionAccessorMock;

  @InjectMocks
  private TransactionService transactionService;

  @Test
  @DisplayName("When fetching a transaction by Id, returns Transaction DTO")
  void givenValidTransactionId_whenGetTransaction_thenReturnsTransactionDto() {
    when(transactionAccessorMock.fetchTransaction(anyLong()))
            .thenReturn(new Transaction());

    Transaction response = this.transactionService.getTransaction(TestConstants.LONG);

    assertThat(response).isNotNull();
  }
}