package com.backend.controllers;


import com.backend.entities.Transaction;
import com.backend.services.TransactionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

  private final TransactionService transactionService;


  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping("/addTransaction")
  public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) {
    // Ensure validation and correct type handling are done at the service level
    Transaction createdTransaction = transactionService.addTransaction(transaction);
    return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
  }

  @PostMapping("/updateTransaction")
  public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) {
    Transaction createdTransaction = transactionService.updateTransaction(transaction);
    return new ResponseEntity<>(createdTransaction, HttpStatus.OK);
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<String> deleteTransaction(@PathVariable Long id) {
    try {
      transactionService.deleteTransaction(id);
      return new ResponseEntity<>("Transaction deleted successfully", HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>("Transaction not found", HttpStatus.NOT_FOUND);
    }
  }
  @GetMapping
  public ResponseEntity<List<Transaction>> getAllTransactions() {
    List<Transaction> transactions = transactionService.getAllTransactions();
    return new ResponseEntity<>(transactions, HttpStatus.OK);
  }
}
