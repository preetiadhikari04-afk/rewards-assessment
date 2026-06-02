package com.retailer.rewards.controller;

import com.retailer.rewards.dto.CustomerRewardResponse;
import com.retailer.rewards.service.RewardService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller exposing reward calculation endpoints.
 */
@RestController
@RequestMapping("/api/rewards")
public class RewardController {

    private final RewardService rewardService;

    /**
     * Creates controller with reward service dependency.
     *
     * @param rewardService service used to calculate rewards
     */
    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    /**
     * Returns monthly and total rewards for all customers in the requested date range.
     *
     * @param startDate report start date
     * @param endDate report end date
     * @return rewards for all customers
     */
    @GetMapping
    public List<CustomerRewardResponse> getRewards(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rewardService.calculateRewards(startDate, endDate);
    }

    /**
     * Returns monthly and total rewards for one customer in the requested date range.
     *
     * @param customerId customer id
     * @param startDate report start date
     * @param endDate report end date
     * @return reward summary for the customer
     */
    @GetMapping("/{customerId}")
    public CustomerRewardResponse getRewardsForCustomer(
            @PathVariable String customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return rewardService.calculateRewardsForCustomer(customerId, startDate, endDate);
    }
}
