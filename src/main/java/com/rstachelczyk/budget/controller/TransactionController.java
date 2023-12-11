package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.model.Transaction;
import com.rstachelczyk.budget.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransaction(@PathVariable long id) {
        return ResponseEntity.ok(this.transactionService.getTransaction(id));
    }

    @GetMapping("/helloWorld")
    public ResponseEntity<String> helloWorld() {
        log.info("Test Log");
        return ResponseEntity.ok("Success");
    }
}
