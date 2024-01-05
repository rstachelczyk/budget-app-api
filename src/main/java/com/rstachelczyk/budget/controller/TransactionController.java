package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.model.Transaction;
import com.rstachelczyk.budget.service.TransactionService;
import com.rstachelczyk.budget.utils.AppConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

  @GetMapping("/")
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

  @GetMapping("/{id}")
  public ResponseEntity<Transaction> getTransaction(@PathVariable long id) {
    return ResponseEntity.ok(
      this.transactionService.getTransaction(id)
    );
  }

  @GetMapping("/helloWorld")
  public ResponseEntity<String> helloWorld() {
    log.info("Test Log");
    return ResponseEntity.ok("Success");
  }
}
