package com.retailer.rewards.dto;

import java.time.LocalDateTime;

/**
 * Standard API error response.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {
}
