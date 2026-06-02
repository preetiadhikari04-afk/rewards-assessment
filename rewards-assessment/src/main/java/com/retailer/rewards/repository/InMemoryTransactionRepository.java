package com.retailer.rewards.repository;

import com.retailer.rewards.model.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * In-memory repository with sample data for demonstrating the assignment.
 */
@Repository
public class InMemoryTransactionRepository implements TransactionRepository {

    private final List<Transaction> transactions = List.of(
            new Transaction(1L, "C001", "Aarav Sharma", BigDecimal.valueOf(120), LocalDate.of(2026, 1, 5)),
            new Transaction(2L, "C001", "Aarav Sharma", BigDecimal.valueOf(75), LocalDate.of(2026, 1, 18)),
            new Transaction(3L, "C001", "Aarav Sharma", BigDecimal.valueOf(150), LocalDate.of(2026, 2, 10)),
            new Transaction(4L, "C001", "Aarav Sharma", BigDecimal.valueOf(45), LocalDate.of(2026, 3, 20)),
            new Transaction(5L, "C001", "Aarav Sharma", BigDecimal.valueOf(220), LocalDate.of(2026, 3, 22)),
            new Transaction(6L, "C002", "Priya Mehta", BigDecimal.valueOf(51), LocalDate.of(2026, 1, 8)),
            new Transaction(7L, "C002", "Priya Mehta", BigDecimal.valueOf(100), LocalDate.of(2026, 2, 14)),
            new Transaction(8L, "C002", "Priya Mehta", BigDecimal.valueOf(101), LocalDate.of(2026, 2, 28)),
            new Transaction(9L, "C002", "Priya Mehta", BigDecimal.valueOf(300), LocalDate.of(2026, 3, 4)),
            new Transaction(10L, "C003", "John Smith", BigDecimal.valueOf(49), LocalDate.of(2026, 1, 1)),
            new Transaction(11L, "C003", "John Smith", BigDecimal.valueOf(50), LocalDate.of(2026, 1, 15)),
            new Transaction(12L, "C003", "John Smith", BigDecimal.valueOf(125.75), LocalDate.of(2026, 2, 21)),
            new Transaction(13L, "C003", "John Smith", BigDecimal.valueOf(89.99), LocalDate.of(2026, 3, 30))
    );

    @Override
    public List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(transaction -> isWithinDateRange(transaction, startDate, endDate))
                .toList();
    }

    @Override
    public List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate startDate, LocalDate endDate) {
        return transactions.stream()
                .filter(transaction -> transaction.getCustomerId().equalsIgnoreCase(customerId))
                .filter(transaction -> isWithinDateRange(transaction, startDate, endDate))
                .toList();
    }

    private boolean isWithinDateRange(Transaction transaction, LocalDate startDate, LocalDate endDate) {
        LocalDate transactionDate = transaction.getTransactionDate();
        return !transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate);
    }
}
