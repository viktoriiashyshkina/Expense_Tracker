package com.backend.services;

import com.backend.entities.Category;
import com.backend.entities.Transaction;
import com.backend.repositories.CategoryRepository;
import com.backend.repositories.TransactionRepository;
import com.backend.utills.TransactionType;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  private final TransactionRepository transactionRepository;
  private final CategoryService categoryService;
  private final CategoryRepository categoryRepository;

  private Double accountBalance = 0.0;

  public TransactionService(TransactionRepository transactionRepository,
      CategoryService categoryService, CategoryRepository categoryRepository) {
    this.transactionRepository = transactionRepository;
    this.categoryService = categoryService;
    this.categoryRepository = categoryRepository;
  }

  public List<Transaction> getAllTransactions() {
    return transactionRepository.findAll();
  }

  public Transaction addTransaction(Transaction transaction) {
    validateTransaction(transaction);

    // Get or create category
    Category category = categoryRepository.findByName(transaction.getCategory().getName())
        .orElseGet(() -> {
          Category newCategory = new Category();
          newCategory.setName(transaction.getCategory().getName());
          newCategory.setDescription(transaction.getCategory().getDescription());
          return categoryService.addCategory(newCategory);
        });

    transaction.setCategory(category);
    updateAccountBalance(transaction);

    return transactionRepository.save(transaction);
  }

  public Transaction updateTransaction(Transaction transaction) {
    validateTransaction(transaction);

    // Check if the transaction exists
    Transaction existingTransaction = transactionRepository.findById(transaction.getId())
        .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

    // Update category if changed
    if (!existingTransaction.getCategory().getName().equals(transaction.getCategory())) {
      Category category = getOrCreateCategory(transaction.getCategory().getName().toString());
      transaction.setCategory(category);
    }

    // Update the account balance
    updateAccountBalance(transaction);

    // Update the transaction and return the updated one
    existingTransaction.setTitle(transaction.getTitle());
    existingTransaction.setAmount(transaction.getAmount());
    existingTransaction.setDate(transaction.getDate());
    existingTransaction.setType(transaction.getType());
    existingTransaction.setCategory(transaction.getCategory());

    return transactionRepository.save(existingTransaction);
  }


  // Helper method to check if category has changed
  private boolean categoryChanged(Transaction existingTransaction, Transaction newTransaction) {
    return !existingTransaction.getCategory().getName().equals(newTransaction.getCategory().getName());
  }



  private Category getOrCreateCategory(String categoryName) {
    return categoryRepository.findByName(categoryName)
        .orElseGet(() -> {
          Category newCategory = new Category();
          newCategory.setName(categoryName);
          return categoryRepository.save(newCategory);
        });
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
