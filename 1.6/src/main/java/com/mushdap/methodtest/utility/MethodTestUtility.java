package com.mushdap.methodtest.utility;

import com.mushdap.methodtest.enums.Instance;
import com.mushdap.methodtest.model.MethodTest;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * MethodTest related helper methods
 */
@SuppressWarnings("WeakerAccess")
public class MethodTestUtility {

    /**
     * Iterate through expression and find parent expression value
     * Parent Expression Example: A.B.C then A is parent expression
     *
     * @param methodTest MethodTest object
     * @return Return parent expression value
     */
    public static Object[] getParentExpressionValue(MethodTest methodTest) {
        if (methodTest.getExpressions() == null) {
            return new Object[]{methodTest.getTargetInstance()};
        }

        Object[] parentExpressions = new Object[methodTest.getExpressions().length];

        for (int i = 0; i < methodTest.getExpressions().length; i++) {
            String expression = methodTest.getExpressions()[i];
            expression = expression.replaceAll("\\[", ".[");
            expression = expression.replaceAll("this.", "");
            String[] fields = StringUtility.Split(expression, '.');

            for (int index = 0; index < fields.length; index++) {
                String field = fields[index].replaceAll("\\(\\)", "");
                if (parentExpressions[i] == null) {
                    parentExpressions[i] = methodTest.getTargetInstance();
                }

                Class<?> parentExpressionClass = parentExpressions[i] == null ? methodTest.getTarget().getType() :
                        parentExpressions[i].getClass();
                Field fieldInfo = TypeUtility.getFieldInfoByName(parentExpressionClass, field);
                Method propertyGetter = TypeUtility.getPropertyGetter(parentExpressionClass, field);
                Method method = MethodUtility.getMethodByName(parentExpressionClass, field);

                if (fieldInfo == null && propertyGetter == null && method == null) {
                    if (TypeUtility.isACollection(parentExpressionClass)) {
                        try {
                            ExpressionParser parser = new SpelExpressionParser();
                            EvaluationContext context = new StandardEvaluationContext(parentExpressions[i]);
                            parentExpressions[i] = parser.parseExpression(fields[index]).getValue(context);
                        } catch (Exception e) {
                            parentExpressions[i] = TypeUtility.getFirstObjectFromCollection(parentExpressions[i]);
                            index--;
                        }
                    } else {
                        break;
                    }
                }

                parentExpressions[i] = setParentExpressionValue(fieldInfo, propertyGetter, method,
                        parentExpressions[i]);
            }
        }
        return parentExpressions;
    }

    /**
     * Method to set test actual output with expression value and expression type
     *
     * @param methodTest       MethodTest object
     * @param expressionValues Expression value is used to determine actual output
     */
    public static void setTestActualOutput(MethodTest methodTest, Object[] expressionValues) {
        for (int i = 0; i < expressionValues.length; i++) {
            try {
                if (SerializeUtility.serialize(methodTest.getExpectedOutputs()[i])
                        .equals(SerializeUtility.serialize(Instance.NotNull)) && expressionValues[i] != null) {
                    methodTest.getActualOutputs()[i] = Instance.NotNull;
                } else if (SerializeUtility.serialize(methodTest.getExpectedOutputs()[i])
                        .equals(SerializeUtility.serialize(Instance.Null)) && expressionValues[i] == null) {
                    methodTest.getActualOutputs()[i] = Instance.Null;
                } else if (expressionValues[i] != null && expressionValues[i].getClass() == Class.class) {
                    methodTest.getActualOutputs()[i] = ((Class<?>) expressionValues[i]).getCanonicalName();
                } else {
                    methodTest.getActualOutputs()[i] = expressionValues[i];
                }
            } catch (Exception e) {
                if (e.getCause() != null) {
                    methodTest.getActualOutputs()[i] = ((Exception) e.getCause()).getClass().getName();
                } else {
                    methodTest.getActualOutputs()[i] = e.getClass().getName();
                }
            }
        }
    }

    private static Object setParentExpressionValue(Field fieldInfo, Method propertyGetter, Method method, Object
            parentExpression) {
        if (fieldInfo != null) {
            fieldInfo.setAccessible(true);
            try {
                if (!Modifier.isStatic(fieldInfo.getModifiers())) {
                    parentExpression = fieldInfo.get(parentExpression);
                } else {
                    parentExpression = fieldInfo.get(null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else if (propertyGetter != null) {
            try {
                propertyGetter.setAccessible(true);
                if (!Modifier.isStatic(propertyGetter.getModifiers())) {
                    parentExpression = propertyGetter.invoke(parentExpression);
                } else {
                    parentExpression = propertyGetter.invoke(null, MethodUtility.getMethodNullParametersValues
                            (propertyGetter));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } else if (method != null) {
            Object[] methodParameters = MethodUtility.getMethodParametersValues(method);
            try {
                method.setAccessible(true);
                if (!Modifier.isStatic(method.getModifiers())) {
                    parentExpression = method.invoke(parentExpression, methodParameters);
                } else {
                    parentExpression = method.invoke(null, methodParameters);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return parentExpression;
    }
}
