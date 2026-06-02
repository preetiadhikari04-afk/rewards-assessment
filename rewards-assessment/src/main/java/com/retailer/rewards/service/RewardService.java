package com.retailer.rewards.service;

import com.retailer.rewards.dto.CustomerRewardResponse;

import java.time.LocalDate;
import java.util.List;

/**
 * Service abstraction for calculating reward points.
 */
public interface RewardService {

    /**
     * Calculates reward points for all customers within a date range.
     *
     * @param startDate start date inclusive
     * @param endDate end date inclusive
     * @return rewards for all customers
     */
    List<CustomerRewardResponse> calculateRewards(LocalDate startDate, LocalDate endDate);

    /**
     * Calculates reward points for a specific customer within a date range.
     *
     * @param customerId customer id
     * @param startDate start date inclusive
     * @param endDate end date inclusive
     * @return reward details for a customer
     */
    CustomerRewardResponse calculateRewardsForCustomer(String customerId, LocalDate startDate, LocalDate endDate);

    /**
     * Calculates reward points for a single transaction amount.
     *
     * @param amount transaction amount
     * @return reward points
     */
    long calculatePoints(java.math.BigDecimal amount);
}
