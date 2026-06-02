package com.retailer.rewards.dto;

import java.time.Month;

/**
 * Monthly reward summary for a customer.
 */
public record MonthlyRewardResponse(
        int year,
        Month month,
        long transactionCount,
        long points
) {
}
