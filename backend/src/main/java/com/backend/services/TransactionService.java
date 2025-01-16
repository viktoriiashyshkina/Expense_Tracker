package com.backend.services;

import com.backend.entities.Category;
import com.backend.entities.Transaction;
import com.backend.repositories.TransactionRepository;
import com.backend.utills.TransactionType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final CategoryService categoryService;

  private Double accountBalance = 0.0;

  public TransactionService(TransactionRepository transactionRepository,
      CategoryService categoryService) {
    this.transactionRepository = transactionRepository;
    this.categoryService = categoryService;
  }

  public List<Transaction> getAllTransactions() {
    return transactionRepository.findAll();
  }

  public Transaction addTransaction(Transaction transaction) {
    validateTransaction(transaction);

    // Set the category of the transaction
    Category category = categoryService.getCategory(transaction.getCategory().getName());
    transaction.setCategory(category);

    // Update the account balance based on the transaction type
    updateAccountBalance(transaction);

    return transactionRepository.save(transaction);
  }

  public Transaction updateTransaction(Transaction transaction) {
    validateTransaction(transaction);

    // Update the account balance based on the transaction type (on update)
    updateAccountBalance(transaction);

    // Update transaction and return the updated one
    return transactionRepository.save(transaction);
  }

  public void deleteTransaction(Long id) {
    transactionRepository.deleteById(id);
  }

  public List<Transaction> getTransactionByType(Transaction transaction) {
    return transactionRepository.findTransactionByType(transaction.getType());
  }

  public List<Transaction> getTransactionByDate(Transaction transaction) {
    return transactionRepository.findTransactionByDate(transaction.getDate());
  }

  public List<Transaction> getTransactionByTitle(Transaction transaction) {
    return transactionRepository.findTransactionByTitleContainingIgnoreCase(transaction.getTitle());
  }

  public List<Transaction> getTransactionByCategory(Transaction transaction) {
    return transactionRepository.findTransactionByCategory(transaction.getCategory());
  }

  // Update the account balance based on the transaction type
  private void updateAccountBalance(Transaction transaction) {
    if (transaction.getType() == TransactionType.INCOME) {
      accountBalance += transaction.getAmount();
    } else if (transaction.getType() == TransactionType.EXPENSE) {
      accountBalance -= transaction.getAmount();
    }
    transaction.setBalance(accountBalance);
  }

  // Validate the transaction (ensure fields are correct)
  private void validateTransaction(Transaction transaction) {
    if (transaction.getTitle() == null || transaction.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Title is required");
    }
    if (transaction.getAmount() <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
    if (transaction.getCategory() == null || transaction.getCategory().getName().isEmpty()) {
      throw new IllegalArgumentException("Category is required");
    }
    if (transaction.getDate() == null) {
      throw new IllegalArgumentException("Date is required");
    }
  }
}
