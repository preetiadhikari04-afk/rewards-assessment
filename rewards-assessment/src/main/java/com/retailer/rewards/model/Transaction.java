package com.retailer.rewards.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a purchase transaction made by a customer.
 */
public class Transaction {

    private final Long id;
    private final String customerId;
    private final String customerName;
    private final BigDecimal amount;
    private final LocalDate transactionDate;

    /**
     * Creates a transaction instance.
     *
     * @param id unique transaction id
     * @param customerId unique customer id
     * @param customerName customer display name
     * @param amount transaction amount in dollars
     * @param transactionDate purchase date
     */
    public Transaction(Long id, String customerId, String customerName, BigDecimal amount, LocalDate transactionDate) {
        this.id = id;
        this.customerId = customerId;
        this.customerName = customerName;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Transaction that = (Transaction) object;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
