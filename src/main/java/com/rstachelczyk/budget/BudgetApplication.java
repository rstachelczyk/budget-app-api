package com.rstachelczyk.budget;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Application class.
 */
@SpringBootApplication
@Slf4j
public class BudgetApplication {

  /**
   * Main application method.
   *
   * @param args the application arguments
   */
  public static void main(String[] args) {
    try {
      SpringApplication.run(BudgetApplication.class, args);
    } catch (Exception e) {
      log.error("Budget Api encountered and exception during startup", e);
    }
  }
}
