package com.mushdap.methodtest.exception;

/**
 * Throw Runtime exception if test method expected output not matched with actual output
 */
public class NoMatchedException extends RuntimeException {
    public NoMatchedException(String message) {
        super(message);
    }
}
