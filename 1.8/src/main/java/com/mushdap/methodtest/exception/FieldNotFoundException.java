package com.mushdap.methodtest.exception;

/**
 * Throw Runtime exception if field is not found in class
 */
public class FieldNotFoundException extends RuntimeException {
    public FieldNotFoundException(String fieldName, String targetClass) {
        super(String.format("%s is not found in %s class", fieldName, targetClass));
    }
}