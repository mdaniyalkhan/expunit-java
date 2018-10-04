package com.mushdap.methodtest.utility;

import com.google.common.collect.ObjectArrays;
import com.mushdap.methodtest.exception.MethodNotFoundException;
import com.mushdap.methodtest.model.MethodInfo;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Utility to extend Java Reflection API Method related operations
 */
@SuppressWarnings("WeakerAccess")
public class MethodUtility {

    /**
     * Get Methods by method name
     *
     * @param type       Class type in which methods reside
     * @param methodName Name of method
     * @return List of matching methods
     */
    public static Method[] getMethodsByName(Class type, String methodName) {
        ArrayList<Method> methods = new ArrayList<Method>();
        for (Method method : getMethods(type)) {
            if (method.getName().matches(methodName)) {
                methods.add(method);
            }
        }

        return methods.toArray(new Method[0]);
    }

    /**
     * Get all methods for specific class
     *
     * @param type class type
     * @return all class and its inherited class methods
     */
    public static Method[] getMethods(Class<?> type) {
        Method[] result = type.getDeclaredMethods();

        Class<?> parentClass = type.getSuperclass();
        if (parentClass != null) {
            Method[] parentClassFields = getMethods(parentClass);
            result = ObjectArrays.concat(result, parentClassFields, Method.class);
        }

        return result;
    }

    /**
     * Get Method by method name
     *
     * @param type       Class type in which method reside
     * @param methodName Name of method
     * @return Matching method
     */
    public static Method getMethodByName(Class type, String methodName) {
        for (Method method : getMethods(type)) {
            if (method.getName().matches(methodName)) {
                return method;
            }
        }

        return null;
    }

    /**
     * Generate default parameter values for method info
     *
     * @param methodInfo MethodInfo object
     * @return Default parameter values for method
     */
    public static Object[] getMethodParametersValues(Method methodInfo) {
        int totalNumberOfFields = methodInfo.getDeclaringClass().getDeclaredFields().length;
        Class<?>[] parameterInfo = methodInfo.getParameterTypes();
        Type[] parameterizeInfo = methodInfo.getGenericParameterTypes();
        Object[] parameters = new Object[parameterInfo.length];
        for (int index = 0; index < parameterInfo.length; index++) {
            Class parameterType = parameterInfo[index];

            if (parameterType == Class.class) {
                parameters[index] = methodInfo.getDeclaringClass();
                continue;
            }
            Object defaultValue = TypeUtility.getValue(parameterInfo[index].getName(), parameterType, index +
                    totalNumberOfFields + 1);
            if (defaultValue != null) {
                parameters[index] = defaultValue;
            } else if (Modifier.isInterface(parameterType.getModifiers()) || Modifier.isAbstract(parameterType
                    .getModifiers())) {
                if (TypeUtility.isACollection(parameterInfo[index]) &&
                        parameterizeInfo[index] instanceof ParameterizedType) {
                    parameters[index] = TypeUtility.getValue((ParameterizedType) parameterizeInfo[index], index +
                            totalNumberOfFields + 1);
                }
            } else {
                parameters[index] = TypeUtility.createInstance(parameterType);
            }
        }
        return parameters;
    }

    /**
     * Generate null parameter values for method info
     *
     * @param methodInfo MethodInfo object
     * @return Default parameter values for method
     */
    public static Object[] getMethodNullParametersValues(Method methodInfo) {
        int totalNumberOfFields = methodInfo.getDeclaringClass().getDeclaredFields().length;
        Class<?>[] parameterInfos = methodInfo.getParameterTypes();
        Object[] parameters = new Object[parameterInfos.length];
        for (int index = 0; index < parameterInfos.length; index++) {
            Class parameterType = parameterInfos[index];
            parameters[index] = TypeUtility.getDefaultValue(parameterType, index + totalNumberOfFields + 1);
        }

        return parameters;
    }

    /**
     * Find and return method from MethodInfo object
     *
     * @param methodInfo MethodInfo object
     * @return Method object
     */
    public static Method getMethod(MethodInfo methodInfo) {
        Method result = null;
        Class target = methodInfo.getMethodTest().getTarget().getType();
        String methodName = methodInfo.getMethodName();
        for (Method method : getMethodsByName(target, methodName)) {
            if (isMethodParametersMatched(methodInfo.getParameterTypes(), method.getParameterTypes())) {
                result = method;
                break;
            }
        }

        if (result == null) {
            result = getMethodByName(target, methodName);

            if (result == null) {
                throw new MethodNotFoundException(methodName, target.getCanonicalName());
            }
        }

        if (methodInfo.getParameterValues().length == 0) {
            methodInfo.setParameterValues(methodInfo.getPassNullParameterValues() ?
                    getMethodNullParametersValues(result) : getMethodParametersValues(result));
        }

        methodInfo.setReturnType(result.getReturnType());

        Object[] expectedOutputs = methodInfo.getMethodTest().getExpectedOutputs();
        if (expectedOutputs != null) {
            for (int i = 0; i < expectedOutputs.length; i++) {
                Object expectedValue = expectedOutputs[i];
                if (expectedValue != null) {
                    methodInfo.getMethodTest().getExpectedOutputs()[i] = TypeUtility.CastValueWithRespectToType
                            (expectedValue, methodInfo.getReturnType());
                }
            }
        }

        return result;
    }

    /**
     * Check method parameters matched with custom user defined parameters
     *
     * @param methodParametersTypes Method parameter types
     * @param matchParametersTypes  User defined parameter types
     * @return True if matched
     */
    public static boolean isMethodParametersMatched(Class[] methodParametersTypes, Class[] matchParametersTypes) {
        if (methodParametersTypes.length != matchParametersTypes.length) {
            return false;
        }

        for (int i = 0; i < methodParametersTypes.length; i++) {
            if (!TypeUtility.getUnBoxType(methodParametersTypes[i]).getName().equals(TypeUtility.getUnBoxType
                    (matchParametersTypes[i]).getName())) {
                return false;
            }
        }

        return true;
    }
}


