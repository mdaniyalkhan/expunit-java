package com.mushdap.methodtest.exception;

/**
 * Throw Runtime exception if test method is not found in class
 */
public class MethodNotFoundException extends RuntimeException {
    public MethodNotFoundException(String methodName, String targetClass) {
        super(String.format("%s is not found in %s class", methodName, targetClass));
    }
}
