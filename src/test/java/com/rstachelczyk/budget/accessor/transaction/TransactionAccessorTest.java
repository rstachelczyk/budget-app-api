package com.rstachelczyk.budget.accessor.transaction;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rstachelczyk.budget.TestConstants;
import com.rstachelczyk.budget.accessor.budget.BudgetEntity;
import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import jakarta.persistence.EntityNotFoundException;
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
    BudgetEntity mockBudget = new BudgetEntity();

    List<TransactionEntity> mockTransactionEntityList = List.of(
        TransactionEntity.builder()
            .id(1L)
            .budget(mockBudget)
            .description("test 1")
            .amount(1000L)
            .type("charge")
            .status("settled")
            .isRecurring(false)
            .build(),
        TransactionEntity.builder()
            .id(2L)
            .budget(mockBudget)
            .description("test 2")
            .amount(1000L)
            .type("charge")
            .status("settled")
            .isRecurring(false)
            .build(),
        TransactionEntity.builder()
            .id(3L)
            .budget(mockBudget)
            .description("test 3")
            .amount(1000L)
            .type("charge")
            .status("settled")
            .isRecurring(false)
            .build()
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
  @DisplayName("When Transaction record doesn't exist, throws ResourceNotFoundException")
  void whenGivenInvalidId_fetchTransaction_throwsResourceNotFoundException() {
    long id = TestConstants.LONG;

    when(transactionRepositoryMock.findById(id)).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class,
        () -> this.transactionAccessor.fetchTransaction(id)
    );
  }

  @Test
  @DisplayName("When creating new transaction, saves entity to db")
  void whenGivenValidTransactionAndBudget_createTransaction_savesEntityToDb() {
    BudgetEntity budget = BudgetEntity.builder()
        .id(1L)
        .name("Test")
        .targetAmount(100L)
        .createdAt(now())
        .updatedAt(now())
        .build();

    TransactionCreateDto params = new TransactionCreateDto(
        "test 1", 1000L, 4L, "charge", "settled", false
    );

    TransactionEntity entity = params.toTransactionEntity();
    entity.setBudget(budget);

    when(this.transactionRepositoryMock.save(any(TransactionEntity.class)))
        .thenReturn(entity);

    when(this.transactionEntityMapperMock.map(any(TransactionEntity.class)))
        .thenReturn(new Transaction());

    Transaction result = this.transactionAccessor.createTransaction(params, budget);

    assertThat(result).isNotNull();
  }

  @Test
  @DisplayName("When Transaction record exists, deletes record")
  void whenGivenValidId_deleteTransaction_successfullyDeletesTransaction() {
    long id = TestConstants.LONG_OBJECT;

    when(transactionRepositoryMock.findById(id)).thenReturn(Optional.of(new TransactionEntity()));

    when(transactionEntityMapperMock.map(any(TransactionEntity.class)))
        .thenReturn(new Transaction());

    doNothing().when(transactionRepositoryMock).deleteById(id);

    this.transactionAccessor.deleteTransaction(id);

    verify(transactionRepositoryMock, times(1)).deleteById(id);
  }

  @Test
  @DisplayName("When Transaction record doesn't exist, throws ResourceNotFoundException")
  void whenGivenInvalidId_deleteTransaction_throwsResourceNotFoundException() {
    long id = TestConstants.LONG;

    when(transactionRepositoryMock.findById(id)).thenReturn(Optional.empty());

    assertThrows(
        EntityNotFoundException.class,
        () -> this.transactionAccessor.deleteTransaction(id)
    );
  }
}
