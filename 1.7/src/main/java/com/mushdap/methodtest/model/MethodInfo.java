package com.mushdap.methodtest.model;

import com.mushdap.methodtest.utility.MethodUtility;
import com.mushdap.methodtest.utility.TypeUtility;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Class contains method to be tested information
 */
public class MethodInfo {

    private String _MethodName;
    private Class[] _ParameterTypes;
    private Object[] _ParameterValues;
    private boolean _PassNullParameterValues;
    private MethodTest _MethodTest;

    private Class _ReturnType;

    private MethodInfo(String methodName, boolean passNullParameterValues, Object[] parameterValues, MethodTest
            methodTest) {
        setupMethodTest(methodName, methodTest);
        setMethodName(methodName);
        setParameterTypes(parameterValues);
        setParameterValues(parameterValues);
        setPassNullParameterValues(passNullParameterValues);
        setMethodTest(methodTest);
    }

    /**
     * Method to create MethodInfo instance usually used to test method with default parameter values
     *
     * @param methodName Name of method to be tested
     * @param methodTest Method test information e.g. Target class, expected output and other parameters
     * @return MethodInfo object
     */
    public static MethodInfo create(String methodName, MethodTest methodTest) {
        return new MethodInfo(methodName, false, new Object[0], methodTest);
    }

    /**
     * Method to create MethodInfo instance usually used to test method with null parameter values
     *
     * @param methodName Name of method to be tested
     * @param methodTest Method test information e.g. Target class, expected output and other parameters
     * @return MethodInfo object
     */
    public static MethodInfo createWithNullParameters(String methodName, MethodTest methodTest) {
        return new MethodInfo(methodName, true, new Object[0], methodTest);
    }

    /**
     * Method to create MethodInfo instance usually used to test method overloads, method custom responses by
     * defining user defined
     * parameter values
     *
     * @param methodName      Name of method to be tested
     * @param parameterValues Parameter user defined values
     * @param methodTest      Method test information e.g. Target class, expected output and other parameters
     * @return MethodInfo object
     */
    public static MethodInfo create(String methodName, Object[] parameterValues, MethodTest methodTest) {
        return new MethodInfo(methodName, false, parameterValues, methodTest);
    }

    private void setupMethodTest(String methodName, MethodTest methodTest) {
        methodTest.setTargetInstance(TypeUtility.createInstance(methodTest.getTarget().getType(), methodTest
                .getTarget().getClassFields()));
        methodTest.setName(String.format("Testing method [%s] in ", methodName));
        methodTest.setMethodInfo(this);
    }

    public String getMethodName() {
        return _MethodName;
    }

    private void setMethodName(String value) {
        _MethodName = value;
    }

    public Class[] getParameterTypes() {
        return _ParameterTypes;
    }

    private void setParameterTypes(Object[] value) {
        if (value != null) {
            _ParameterTypes = new Class[value.length];
            for (int i = 0; i < value.length; i++) {
                _ParameterTypes[i] = value[i].getClass();
            }
        }
    }

    public Object[] getParameterValues() {
        return _ParameterValues;
    }

    public void setParameterValues(Object[] value) {
        _ParameterValues = value;
    }

    public boolean getPassNullParameterValues() {
        return _PassNullParameterValues;
    }

    private void setPassNullParameterValues(boolean value) {
        _PassNullParameterValues = value;
    }

    public MethodTest getMethodTest() {
        return _MethodTest;
    }

    private void setMethodTest(MethodTest value) {
        _MethodTest = value;
    }

    public Class getReturnType() {
        return _ReturnType;
    }

    public void setReturnType(Class returnType) {
        _ReturnType = returnType;
    }

    /**
     * Test method
     */
    public void test() {
        invokeMethod();
        _MethodTest.setActualOutputs();
    }

    /**
     * Verify method expected output equals to actual output
     */
    public void verify() {
        getMethodTest().verify(getMethodName());
    }

    /**
     * Invoke test method and set target instance
     */
    private void invokeMethod() {
        Method methodInfo = MethodUtility.getMethod(this);

        if (methodInfo != null) {
            MethodTest methodTest = getMethodTest();
            methodInfo.setAccessible(true);

            try {
                if (methodInfo.getReturnType() != void.class) {
                    if (Modifier.isStatic(methodInfo.getModifiers())) {
                        methodTest.setTargetInstance(methodInfo.invoke(null, getParameterValues()));
                    } else {
                        methodTest.setTargetInstance(methodInfo.invoke(methodTest.getTargetInstance(),
                                getParameterValues()));
                    }
                } else {
                    if (Modifier.isStatic(methodInfo.getModifiers())) {
                        methodInfo.invoke(null, getParameterValues());
                    } else {
                        methodInfo.invoke(methodTest.getTargetInstance(), getParameterValues());
                    }
                }
            } catch (Exception e) {
                if (e.getCause() != null) {
                    methodTest.getActualOutputs()[0] = e.getCause().getClass().getSimpleName();
                    methodTest.setErrorMessages();
                } else {
                    methodTest.getActualOutputs()[0] = e.getClass().getSimpleName();
                    methodTest.setErrorMessages();
                }
            }
        }
    }

    /**
     * Test and verify
     */
    public void testAndVerify() {
        test();
        verify();
    }
}


