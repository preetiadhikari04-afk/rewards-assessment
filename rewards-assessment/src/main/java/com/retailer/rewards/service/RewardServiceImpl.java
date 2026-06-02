package com.retailer.rewards.service;

import com.retailer.rewards.dto.CustomerRewardResponse;
import com.retailer.rewards.dto.MonthlyRewardResponse;
import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.model.Transaction;
import com.retailer.rewards.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Default reward calculation service implementation.
 */
@Service
public class RewardServiceImpl implements RewardService {

    private static final BigDecimal FIRST_REWARD_LIMIT = BigDecimal.valueOf(50);
    private static final BigDecimal SECOND_REWARD_LIMIT = BigDecimal.valueOf(100);

    private final TransactionRepository transactionRepository;

    /**
     * Creates reward service with transaction repository dependency.
     *
     * @param transactionRepository repository containing purchase records
     */
    public RewardServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public List<CustomerRewardResponse> calculateRewards(LocalDate startDate, LocalDate endDate) {
        validateDateRange(startDate, endDate);
        List<Transaction> transactions = transactionRepository.findByTransactionDateBetween(startDate, endDate);
        return buildCustomerRewards(transactions);
    }

    @Override
    public CustomerRewardResponse calculateRewardsForCustomer(String customerId, LocalDate startDate, LocalDate endDate) {
        validateCustomerId(customerId);
        validateDateRange(startDate, endDate);
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, startDate, endDate);
        if (transactions.isEmpty()) {
            throw new CustomerNotFoundException("No transactions found for customerId: " + customerId);
        }
        return buildCustomerRewards(transactions).get(0);
    }

    @Override
    public long calculatePoints(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Transaction amount must be zero or positive");
        }
        long roundedDownAmount = amount.longValue();
        long pointsAboveHundred = Math.max(roundedDownAmount - SECOND_REWARD_LIMIT.longValue(), 0) * 2;
        long pointsBetweenFiftyAndHundred = Math.max(Math.min(roundedDownAmount, SECOND_REWARD_LIMIT.longValue())
                - FIRST_REWARD_LIMIT.longValue(), 0);
        return pointsAboveHundred + pointsBetweenFiftyAndHundred;
    }

    private List<CustomerRewardResponse> buildCustomerRewards(List<Transaction> transactions) {
        Map<String, List<Transaction>> transactionsByCustomer = transactions.stream()
                .collect(Collectors.groupingBy(Transaction::getCustomerId));

        return transactionsByCustomer.entrySet().stream()
                .map(entry -> buildCustomerRewardResponse(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(CustomerRewardResponse::customerId))
                .toList();
    }

    private CustomerRewardResponse buildCustomerRewardResponse(String customerId, List<Transaction> customerTransactions) {
        String customerName = customerTransactions.get(0).getCustomerName();
        Map<YearMonth, List<Transaction>> transactionsByMonth = customerTransactions.stream()
                .collect(Collectors.groupingBy(transaction -> YearMonth.from(transaction.getTransactionDate())));

        List<MonthlyRewardResponse> monthlyRewards = transactionsByMonth.entrySet().stream()
                .map(entry -> buildMonthlyRewardResponse(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(MonthlyRewardResponse::year).thenComparing(MonthlyRewardResponse::month))
                .toList();

        long totalPoints = monthlyRewards.stream()
                .mapToLong(MonthlyRewardResponse::points)
                .sum();

        return new CustomerRewardResponse(customerId, customerName, monthlyRewards, totalPoints);
    }

    private MonthlyRewardResponse buildMonthlyRewardResponse(YearMonth yearMonth, List<Transaction> transactions) {
        long monthlyPoints = transactions.stream()
                .map(Transaction::getAmount)
                .mapToLong(this::calculatePoints)
                .sum();
        return new MonthlyRewardResponse(yearMonth.getYear(), yearMonth.getMonth(), transactions.size(), monthlyPoints);
    }

    private void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("startDate and endDate are required");
        }
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("startDate must be before or equal to endDate");
        }
    }

    private void validateCustomerId(String customerId) {
        if (customerId == null || customerId.isBlank()) {
            throw new IllegalArgumentException("customerId must not be blank");
        }
    }
}
