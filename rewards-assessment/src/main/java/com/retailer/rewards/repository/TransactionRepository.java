package com.retailer.rewards.repository;

import com.retailer.rewards.model.Transaction;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository abstraction for retrieving customer transactions.
 */
public interface TransactionRepository {

    /**
     * Finds all transactions between the provided dates, inclusive.
     *
     * @param startDate lower transaction date boundary
     * @param endDate upper transaction date boundary
     * @return matching transactions
     */
    List<Transaction> findByTransactionDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Finds all transactions for one customer between the provided dates, inclusive.
     *
     * @param customerId customer id
     * @param startDate lower transaction date boundary
     * @param endDate upper transaction date boundary
     * @return matching customer transactions
     */
    List<Transaction> findByCustomerIdAndTransactionDateBetween(String customerId, LocalDate startDate, LocalDate endDate);
}
