package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import com.rstachelczyk.budget.dto.TransactionUpdateDto;
import com.rstachelczyk.budget.service.TransactionService;
import com.rstachelczyk.budget.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Transaction Controller.
 */
@RestController
@Slf4j
@RequestMapping("api/v1/transaction")
public class TransactionController {

  private final TransactionService transactionService;

  @Autowired
  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  /**
   * Fetch list of transactions with pagination & sorting.
   *
   * @param page page number
   * @param limit page limit
   * @param sortBy field to sort by
   * @param sortDir direction to sort in (asc, desc)
   * @return page of transactions
   */
  // CHECKSTYLE:OFF
  @GetMapping("")
  public ResponseEntity<Page<Transaction>> getTransactions(
      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) Integer page,
      @RequestParam(value = "limit", defaultValue = AppConstants.DEFAULT_PAGE_LIMIT, required = false) Integer limit,
      @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir
  ) {
    log.info("Fetching Transactions");
    return ResponseEntity.ok(
      this.transactionService.getTransactions(page, limit, sortBy, sortDir)
    );
  }
  // CHECKSTYLE:ON

  /**
   * Fetch transaction by id.
   *
   * @param id transaction id
   * @return transaction
   */
  @GetMapping("/{id}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable("id") final long id) {
    log.info("Fetching Transaction with id:" + id);
    return ResponseEntity.ok(
      this.transactionService.getTransaction(id)
    );
  }

  /**
   * Create new transaction.
   *
   * @param request transaction params
   * @return created transaction resource
   */
  @PostMapping("")
  public ResponseEntity<Transaction> createTransaction(
      @Valid @RequestBody TransactionCreateDto request
  ) {
    log.info("Creating new transaction with params: " + request);
    return new ResponseEntity<>(
      this.transactionService.createTransaction(request),
      HttpStatus.CREATED
    );
  }

  /**
   * Update transaction by id.
   *
   * @param id transaction id
   * @return transaction
   */
  @PutMapping("/{id}")
  public ResponseEntity<Transaction> updateTransaction(
      @PathVariable("id") final long id,
      @Valid @RequestBody TransactionUpdateDto request
  ) {
    log.info("Updating transaction of Id: " + id + "with params: " + request);
    return ResponseEntity.ok(
      this.transactionService.updateTransaction(id, request)
    );
  }

  /**
   * Delete transaction by id.
   *
   * @param id transaction id
   * @return transaction
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable("id") final long id) {
    this.transactionService.deleteTransaction(id);

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/helloWorld")
  public ResponseEntity<String> helloWorld() {
    log.info("Test Log");
    return ResponseEntity.ok("Success");
  }
}
