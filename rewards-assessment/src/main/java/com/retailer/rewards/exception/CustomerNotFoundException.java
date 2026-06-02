package com.retailer.rewards.exception;

/**
 * Exception thrown when requested customer reward data is unavailable.
 */
public class CustomerNotFoundException extends RuntimeException {

    /**
     * Creates an exception with a detailed message.
     *
     * @param message exception message
     */
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
