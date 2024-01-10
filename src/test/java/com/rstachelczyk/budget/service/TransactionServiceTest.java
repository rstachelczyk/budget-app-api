package com.rstachelczyk.budget.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.accessor.transaction.TransactionAccessor;
import com.rstachelczyk.budget.dto.Transaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

  @Mock
  private TransactionAccessor transactionAccessorMock;

  @InjectMocks
  private TransactionService transactionService;

  @Test
  @DisplayName("When fetching transaction list, returns Page of Transaction DTO")
  void givenPageAndSortingParams_whenGetTransactions_thenReturnsTransactionsPage() {
    List<Transaction> transactionList = new ArrayList<>();
    Page<Transaction> pagedResult = new PageImpl<>(transactionList);

    when(transactionAccessorMock.fetchTransactions(any(Pageable.class)))
      .thenReturn(pagedResult);

    Page<Transaction> response = this.transactionService.getTransactions(
      0, 20, "id", "desc"
    );

    assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("When fetching a transaction by Id, returns Transaction DTO")
  void givenValidTransactionId_whenGetTransaction_thenReturnsTransactionDto() {
    when(transactionAccessorMock.fetchTransaction(anyLong()))
            .thenReturn(new Transaction());

    Transaction response = this.transactionService.getTransaction(TestConstants.LONG);

    assertThat(response).isNotNull();
  }
}
