package com.github.haz00.rssfeed;

/**
 * Specific type of exception
 */
public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }
}
