package com.backend.repositories;

import com.backend.entities.Category;
import com.backend.entities.Transaction;
import com.backend.utills.TransactionType;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
  List<Transaction> findTransactionByType(TransactionType type);

  List<Transaction> findTransactionByDate(LocalDateTime date);

  List<Transaction> findTransactionByDateBetween(LocalDateTime startDate, LocalDateTime endDate);

  List<Transaction> findTransactionByCategory(Category category);

  List<Transaction> findTransactionByTitleContainingIgnoreCase(String title);

}
