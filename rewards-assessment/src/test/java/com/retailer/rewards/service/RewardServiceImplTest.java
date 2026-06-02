package com.retailer.rewards.service;

import com.retailer.rewards.exception.CustomerNotFoundException;
import com.retailer.rewards.repository.InMemoryTransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RewardServiceImplTest {

    private RewardService rewardService;

    @BeforeEach
    void setUp() {
        rewardService = new RewardServiceImpl(new InMemoryTransactionRepository());
    }

    @Test
    void calculatePointsShouldReturnZeroWhenAmountIsLessThanOrEqualToFifty() {
        assertEquals(0, rewardService.calculatePoints(BigDecimal.valueOf(49)));
        assertEquals(0, rewardService.calculatePoints(BigDecimal.valueOf(50)));
    }

    @Test
    void calculatePointsShouldReturnOnePointPerDollarBetweenFiftyAndHundred() {
        assertEquals(25, rewardService.calculatePoints(BigDecimal.valueOf(75)));
        assertEquals(50, rewardService.calculatePoints(BigDecimal.valueOf(100)));
    }

    @Test
    void calculatePointsShouldReturnTwoPointsPerDollarAboveHundredPlusFiftyBasePoints() {
        assertEquals(90, rewardService.calculatePoints(BigDecimal.valueOf(120)));
        assertEquals(150, rewardService.calculatePoints(BigDecimal.valueOf(150)));
    }

    @Test
    void calculatePointsShouldRoundDownDecimalAmounts() {
        assertEquals(100, rewardService.calculatePoints(BigDecimal.valueOf(125.75)));
    }

    @Test
    void calculateRewardsForCustomerShouldReturnMonthlyAndTotalPoints() {
        var response = rewardService.calculateRewardsForCustomer(
                "C001", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 31));

        assertEquals("C001", response.customerId());
        assertEquals(3, response.monthlyRewards().size());
        assertEquals(555, response.totalPoints());
    }

    @Test
    void calculateRewardsShouldThrowExceptionForInvalidDateRange() {
        assertThrows(IllegalArgumentException.class, () -> rewardService.calculateRewards(
                LocalDate.of(2026, 4, 1), LocalDate.of(2026, 3, 1)));
    }

    @Test
    void calculatePointsShouldThrowExceptionForNegativeAmount() {
        assertThrows(IllegalArgumentException.class, () -> rewardService.calculatePoints(BigDecimal.valueOf(-1)));
    }

    @Test
    void calculateRewardsForCustomerShouldThrowExceptionWhenCustomerNotFound() {
        assertThrows(CustomerNotFoundException.class, () -> rewardService.calculateRewardsForCustomer(
                "C999", LocalDate.of(2026, 1, 1), LocalDate.of(2026, 3, 31)));
    }
}
