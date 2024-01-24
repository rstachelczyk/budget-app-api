package com.rstachelczyk.budget.controller;

import com.rstachelczyk.budget.dto.Budget;
import com.rstachelczyk.budget.service.BudgetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Budget Controller.
 */
@RestController
@Slf4j
@RequestMapping("api/v1/budget")
public class BudgetController {
  private final BudgetService budgetService;

  @Autowired
  public BudgetController(BudgetService budgetService) {
    this.budgetService = budgetService;
  }

  @GetMapping("")
  public ResponseEntity<List<Budget>> getBudgets() {
    log.info("Fetching all budgets for user");
    return ResponseEntity.ok(
      this.budgetService.getBudgets()
    );
  }

}
