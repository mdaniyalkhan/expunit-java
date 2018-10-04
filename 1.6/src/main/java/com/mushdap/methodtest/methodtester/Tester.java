package com.mushdap.methodtest.methodtester;

import com.mushdap.methodtest.model.ClassInfo;
import com.mushdap.methodtest.model.MethodInfo;
import com.mushdap.methodtest.model.MethodTest;
import com.mushdap.methodtest.utility.MethodUtility;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Method Tester to test and verify methods
 */
@SuppressWarnings("WeakerAccess")
public class Tester {
    private ArrayList<String> _methodNamesToBeSkipped;

    /**
     * Default Constructor
     */
    public Tester() {
        _methodNamesToBeSkipped = new ArrayList<String>();
    }

    /**
     * Method names to be skipped by method tester testing
     *
     * @param methodNames List of method names
     */
    public void addMethodNamesToBeSkipped(String[] methodNames) {
        Collections.addAll(_methodNamesToBeSkipped, methodNames);
    }

    /**
     * Method to test defined methods
     *
     * @param methodsToBeTested List of methods to be tested
     */
    public void testMethods(ArrayList<MethodInfo> methodsToBeTested) {
        for (MethodInfo methodToBeTested : methodsToBeTested) {
            if (!isMethodToBeSkipped(methodToBeTested)) {
                methodToBeTested.test();
            }
        }
    }

    /**
     * Test all methods in class
     *
     * @param type           Class type
     * @param expectedOutput expected output
     */
    public void testAllMethods(Class type, Object expectedOutput) {
        ArrayList<MethodInfo> methodsToBeTested = new ArrayList<MethodInfo>();
        for (Method method : type.getDeclaredMethods()) {
            methodsToBeTested.add(MethodInfo.create(method.getName(),
                    MethodUtility.getMethodParametersValues(method), MethodTest.create(ClassInfo.create(type),
                            expectedOutput)));
        }

        testAndVerifyMethods(methodsToBeTested);
    }

    /**
     * Verify methods expected output equals to actual output
     *
     * @param methodsToBeVerify List of methods to Be verify
     */
    public void verifyTests(ArrayList<MethodInfo> methodsToBeVerify) {
        for (MethodInfo methodToBeVerify : methodsToBeVerify) {
            if (!isMethodToBeSkipped(methodToBeVerify)) {
                methodToBeVerify.verify();
            }
        }

    }

    private boolean isMethodToBeSkipped(MethodInfo methodToBeTested) {
        Class target = methodToBeTested.getMethodTest().getTarget().getType();
        String targetClassName = target.getName();
        String methodName = methodToBeTested.getMethodName();
        return Modifier.isAbstract(target.getModifiers()) ||
                Modifier.isInterface(target.getModifiers()) ||
                _methodNamesToBeSkipped.contains(methodName) ||
                _methodNamesToBeSkipped.contains(String.format("%s.%s", targetClassName, methodName)) ||
                _methodNamesToBeSkipped.contains(String.format("%s.*", targetClassName));
    }

    /**
     * Test and verify methods
     *
     * @param methodsToBeTest Methods to be test
     */
    public void testAndVerifyMethods(ArrayList<MethodInfo> methodsToBeTest) {
        testMethods(methodsToBeTest);
        verifyTests(methodsToBeTest);
    }
}



