package com.mushdap.methodtest.utility;


import com.mushdap.methodtest.enums.Instance;
import com.mushdap.methodtest.model.MethodInfo;
import com.mushdap.methodtest.model.MethodTest;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExpressionUtility {

    public static Object[] resolveExpressionValue(MethodTest methodTest) {
        if (methodTest.getExpressions() == null) {
            return MethodTestUtility.getParentExpressionValue(methodTest);
        }

        Object[] expressionValues = new Object[methodTest.getExpressions().length];

        for (int i = 0; i < expressionValues.length; i++) {
            String current = methodTest.getExpressions()[i];

            if (current.equals("this")) {
                expressionValues[i] = methodTest.getTargetInstance();
                continue;
            }

            String characterInBetweenSingleColon = "'.*?'";
            List<String> matchesConstantExpressions = allMatches(characterInBetweenSingleColon, current);
            current = current.replaceAll(characterInBetweenSingleColon, "#ContantExpr#");
            String fieldPattern = "@f[(]((.*?)+)[)]";
            List<String> fields = allMatches(fieldPattern, current);
            for (String field : fields) {
                String pattern = field.replace("(", "\\(").replace(")", "\\)");
                Object fieldValue = TypeUtility.getFieldValue(field.replaceAll(fieldPattern, "$1"),
                        methodTest.getTarget().getType());
                current = current.replaceAll(pattern, TypeUtility.typeToString(fieldValue));
            }

            current = current.replaceAll("\\(\\)\\.", ".");
            String[] expressions = current.replaceAll("\\s", "")
                    .split("!\\(|\\.equals\\(|\\.charAt\\(|[(]|[)" +
                            "]|[!]|\\+\\+|--|[+]|[-]|[*]|[/]|[%]|<=|>=|[<]|[>]|[!=]|==|&&|[{]|[}]|[,]|[?]|[:]|\\|\\|");
            String[] methodExpr = methodTest.getExpressions();
            methodTest.setExpressions(expressions);
            Object[] values = MethodTestUtility.getParentExpressionValue(methodTest);
            methodTest.setExpressions(methodExpr);
            if (current.equals("@p0")) {
                expressionValues[i] = methodTest.getMethodInfo().getParameterValues()[0];
            } else if (values.length == 1 && values[0] != methodTest.getTargetInstance()) {
                expressionValues[i] = values[0];
            } else if (values.length == 1) {
                try {
                    ExpressionParser parser = new SpelExpressionParser();
                    expressionValues[i] = parser.parseExpression(methodTest.getExpressions()[i]).getValue();
                } catch (Exception e) {
                    expressionValues[i] = Instance.NotFound;
                }
            } else if (values.length > 1) {
                Map<String, Object> expressionsWithValues = new TreeMap<>(comparator());
                for (int j = 0; j < expressions.length; j++) {
                    if (values[j] == methodTest.getTargetInstance()) {
                        continue;
                    }

                    values[j] = setValue(values[j]);
                    expressionsWithValues.putIfAbsent(expressions[j], values[j]);
                }

                for (Map.Entry<String, Object> entry : expressionsWithValues.entrySet()) {
                    current = current.replaceAll("\\b" + entry.getKey() + "\\b", entry.getValue().toString());
                }

                MethodInfo methodInfo = methodTest.getMethodInfo();
                for (int parameterIndex = 0; parameterIndex < methodInfo.getParameterValues().length;
                     parameterIndex++) {
                    String name = "@p" + parameterIndex;
                    Object value = setValue(methodInfo.getParameterValues()[parameterIndex]);
                    current = current.replaceAll(name, value != null ? value.toString() : "null");
                }

                current = current.replaceAll("\\(\\)", "");
                current = current.replaceAll("charAt\\(([0-9]+)\\)", "charAt($1).toString()");
                current = current.replaceAll("String.valueOf\\(((.*?)+)\\)", "'$1'");
                current = current.replaceAll("2147483648L", "2147483648");
                current = current.replaceAll("2147483648", "2147483648L");

                for (String expr : matchesConstantExpressions) {
                    current = current.replaceFirst("#ContantExpr#", expr);
                }

                ExpressionParser parser = new SpelExpressionParser();
                expressionValues[i] = parser.parseExpression(current).getValue();
            }

            if (expressionValues[i] != null && expressionValues[i].getClass() == Class.class) {
                expressionValues[i] = ((Class<?>) expressionValues[i]).getCanonicalName();
            }

            if (expressionValues[i] != null && methodTest.getMethodInfo() != null) {
                expressionValues[i] = TypeUtility.CastValueWithRespectToType(expressionValues[i], methodTest
                        .getMethodInfo().getReturnType());
            }
        }

        return expressionValues;
    }

    private static List<String> allMatches(String pattern, String str) {
        List<String> allMatches = new ArrayList<>();
        Matcher m = Pattern.compile(pattern).matcher(str);
        while (m.find()) {
            allMatches.add(m.group());
        }

        return allMatches;
    }

    @SuppressWarnings("ConstantConditions")
    private static Object setValue(Object val) {
        if (val == null) {
            return null;
        }

        if (val.getClass() == String.class) {
            val = "'" + val + "'";
        } else if (TypeUtility.getValue(val.getClass()) == null) {
            val = SerializeUtility.serialize(val);
        }

        if (TypeUtility.getUnBoxType(val.getClass()) == char.class) {
            val = "'" + Character.getNumericValue((Character) val) + "'";
        }
        if (val.getClass().isArray()) {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            int length = Array.getLength(val);
            for (int index = 0; index < length; index++) {
                Object value = Array.get(val, index);
                builder.append(value.getClass() == String.class ? "'" + value + "'" : value);
                if (index < length - 1) {
                    builder.append(",");
                }
            }

            builder.append("}");
            val = builder.toString();
        }

        return val;
    }

    private static Comparator<String> comparator() {
        return (s1, s2) -> {
            if (s1.length() > s2.length()) {
                return -1;
            } else if (s1.length() < s2.length()) {
                return 1;
            } else {
                return s1.compareTo(s2);
            }
        };
    }
}
