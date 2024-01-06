package com.rstachelczyk.budget.accessor.transaction;

import static java.time.OffsetDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.exception.TransactionNotFoundException;
import com.rstachelczyk.budget.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TransactionAccessorTest {

  @Mock
  private TransactionRepository transactionRepositoryMock;

  @Mock
  private TransactionEntityMapper transactionEntityMapperMock;

  @InjectMocks
  private TransactionAccessor transactionAccessor;

  @Test
  @DisplayName("When given pagination params, fetches transactions and converts to DTO")
  void whenGivenPageable_fetchTransactions_successfullyReturnsPageOfTransactionDTO() {
    Pageable page = PageRequest.of(0, 20);

    List<TransactionEntity> mockTransactionEntityList = List.of(
      new TransactionEntity(1L, "test 1", 1000L, now()),
      new TransactionEntity(2L, "test 2", 1000L, now()),
      new TransactionEntity(3L, "test 3", 1000L, now())
    );
    Page<TransactionEntity> mockRepositoryResponse = new PageImpl<>(mockTransactionEntityList);

    when(transactionRepositoryMock.findAll(page)).thenReturn(mockRepositoryResponse);

    Page<Transaction> result = this.transactionAccessor.fetchTransactions(page);

    assertThat(result.getSize()).isEqualTo(mockRepositoryResponse.getSize());
  }

  @Test
  @DisplayName("When Transaction record exists, finds record and maps to DTO")
  void whenGivenValidId_fetchTransaction_successfullyReturnsTransactionDto() {
    long id = TestConstants.LONG_OBJECT;

    when(transactionRepositoryMock.findById(id)).thenReturn(Optional.of(new TransactionEntity()));

    when(transactionEntityMapperMock.map(any(TransactionEntity.class)))
        .thenReturn(new Transaction());

    Transaction response = this.transactionAccessor.fetchTransaction(id);

    assertThat(response).isNotNull();
  }

  @Test
  @DisplayName("When Transaction record doesn't exist, throws TransactionNotFoundException")
  void whenGivenInvalidId_fetchTransaction_successfullyReturnsTransactionDto() {
    long id = TestConstants.LONG;

    when(transactionRepositoryMock.findById(id)).thenReturn(Optional.empty());

    assertThrows(
            TransactionNotFoundException.class,
            () -> this.transactionAccessor.fetchTransaction(id)
    );
  }
}
