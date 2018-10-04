package com.mushdap.methodtest.exception;

/**
 * Empty Exception used for defining fields with Exception types
 */
public class EmptyException extends RuntimeException {
    public EmptyException(String field, int fieldIndex) {
        super(String.format("Test Exception for %s(%s)", field, fieldIndex));
    }
}
