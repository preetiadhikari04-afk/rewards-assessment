package com.retailer.rewards.dto;

import java.util.List;

/**
 * Reward response containing monthly and total points for one customer.
 */
public record CustomerRewardResponse(
        String customerId,
        String customerName,
        List<MonthlyRewardResponse> monthlyRewards,
        long totalPoints
) {
}
