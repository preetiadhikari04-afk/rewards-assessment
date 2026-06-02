package com.retailer.rewards.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RewardControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRewardsShouldReturnAllCustomersRewardSummary() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].customerId", is("C001")))
                .andExpect(jsonPath("$[0].totalPoints", is(555)));
    }

    @Test
    void getRewardsForCustomerShouldReturnOneCustomerRewardSummary() throws Exception {
        mockMvc.perform(get("/api/rewards/C002")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId", is("C002")))
                .andExpect(jsonPath("$.totalPoints", is(553)));
    }

    @Test
    void getRewardsShouldReturnBadRequestForInvalidDateRange() throws Exception {
        mockMvc.perform(get("/api/rewards")
                        .param("startDate", "2026-04-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    void getRewardsForCustomerShouldReturnNotFoundForUnknownCustomer() throws Exception {
        mockMvc.perform(get("/api/rewards/C999")
                        .param("startDate", "2026-01-01")
                        .param("endDate", "2026-03-31"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status", is(404)));
    }
}
