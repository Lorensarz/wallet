package com.petrov.wallet.impl.exception;

/**
 * Bad request exception.
 */
public class ValidationException extends RuntimeException {

    public ValidationException(String message) {
        super(message);
    }
}
