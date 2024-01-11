package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.dto.Transaction;
import com.rstachelczyk.budget.dto.TransactionCreateDto;
import com.rstachelczyk.budget.service.TransactionService;
import com.rstachelczyk.budget.utils.AppConstants;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    return new ResponseEntity<>(
      this.transactionService.createTransaction(request),
      HttpStatus.CREATED
    );
  }

  @GetMapping("/helloWorld")
  public ResponseEntity<String> helloWorld() {
    log.info("Test Log");
    return ResponseEntity.ok("Success");
  }
}
