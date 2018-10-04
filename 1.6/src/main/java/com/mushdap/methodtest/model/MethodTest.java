package com.mushdap.methodtest.model;

import com.mushdap.methodtest.exception.NoMatchedException;
import com.mushdap.methodtest.utility.ExpressionUtility;
import com.mushdap.methodtest.utility.MethodTestUtility;
import com.mushdap.methodtest.utility.SerializeUtility;
import com.mushdap.methodtest.utility.StringUtility;

/**
 * Method test definition class. Use this class in order to define different method tests.
 */
@SuppressWarnings("All")
public class MethodTest {

    private String _Name;
    private ClassInfo _Target;
    private String[] _Expressions;
    private Object _TargetInstance;

    private Object _ClassInstance;
    private MethodInfo _MethodInfo;

    private Object[] _ExpectedOutputs;
    private Object[] _ActualOutputs;
    private String[] _ErrorMessages;

    private String[] _OutputExpressions;

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param target         Target class in which method reside
     * @param expectedOutput Expected output of method
     * @return MethodTest object
     */
    public static MethodTest create(Class target, Object expectedOutput) {
        return create(ClassInfo.create(target), expectedOutput);
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param target           Target class in which method reside
     * @param outputExpression Expression e.g. field, method or property to get Expected output of method
     * @return MethodTest object
     */
    public static MethodTest createWithOutputExpression(Class target, String outputExpression) {
        return createWithOutputExpression(ClassInfo.create(target), outputExpression);
    }

    /**
     * Create MethodTest instance usually used to test method with specific class return type
     *
     * @param expression     Field or property name separated by '.'
     * @param target         Target class in which method reside
     * @param expectedOutput Expected output of expression
     * @return MethodTest object
     */
    public static MethodTest create(String expression, Class target, Object expectedOutput) {
        return create(expression, ClassInfo.create(target), expectedOutput);
    }

    /**
     * Create MethodTest instance usually used to test method output with method 1st parameter value e.g. setters
     *
     * @param expression Field or property name separated by '.'
     * @param target     Target class in which method reside
     * @return MethodTest object
     */
    public static MethodTest create(String expression, Class target) {
        return create(expression, ClassInfo.create(target));
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param expression       Field or property name separated by '.'
     * @param target           Target class in which method reside
     * @param outputExpression Expression e.g. field, method or property to get Expected output of method
     * @return MethodTest object
     */
    public static MethodTest createWithOutputExpression(String expression, Class target, String outputExpression) {
        return createWithOutputExpression(expression, ClassInfo.create(target), outputExpression);
    }

    /**
     * Create MethodTest instance usually used to test method with specific class return type
     *
     * @param expressions     Fields or properties name separated by '.'
     * @param target          Target class in which method reside
     * @param expectedOutputs Expected output of expressions
     * @return MethodTest object
     */
    public static MethodTest create(String[] expressions, Class target, Object[] expectedOutputs) {
        return create(expressions, ClassInfo.create(target), expectedOutputs);
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param expressions       Field or property name separated by '.'
     * @param target            Target class in which method reside
     * @param outputExpressions Expression e.g. field, method or property to get Expected output of method
     * @return MethodTest object
     */
    public static MethodTest createWithOutputExpression(String[] expressions, Class target, String[]
            outputExpressions) {
        return createWithOutputExpression(expressions, ClassInfo.create(target), outputExpressions);
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param target         Target class in which method reside
     * @param expectedOutput Expected output of method
     * @return MethodTest object
     */
    public static MethodTest create(ClassInfo target, Object expectedOutput) {
        MethodTest methodTest = new MethodTest();

        methodTest.setTarget(target);
        methodTest.setExpectedOutputs(new Object[]{expectedOutput});
        methodTest._ErrorMessages = new String[1];
        methodTest._ActualOutputs = new Object[1];

        return methodTest;
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param target           Target class in which method reside
     * @param outputExpression Expression e.g. field, method or property to get Expected output of method
     * @return MethodTest object
     */
    @SuppressWarnings("SameParameterValue")
    public static MethodTest createWithOutputExpression(ClassInfo target, String outputExpression) {
        MethodTest methodTest = new MethodTest();

        methodTest.setTarget(target);
        methodTest.setOutputExpressions(new String[]{outputExpression});
        methodTest._ErrorMessages = new String[1];
        methodTest._ActualOutputs = new Object[1];

        return methodTest;
    }

    /**
     * Create MethodTest instance usually used to test method with specific class return type
     *
     * @param expression     Field or property name separated by '.'
     * @param target         Target class in which method reside
     * @param expectedOutput Expected output of expression
     * @return MethodTest object
     */
    public static MethodTest create(String expression, ClassInfo target, Object expectedOutput) {
        MethodTest methodTest = new MethodTest();

        methodTest.setExpressions(new String[]{expression});
        methodTest.setTarget(target);
        methodTest.setExpectedOutputs(new Object[]{expectedOutput});
        methodTest._ErrorMessages = new String[1];
        methodTest._ActualOutputs = new Object[1];

        return methodTest;
    }

    /**
     * Create MethodTest instance usually used to test method output with method 1st parameter value e.g. setters
     *
     * @param expression Field or property name separated by '.'
     * @param target     Target class in which method reside
     * @return MethodTest object
     */
    @SuppressWarnings("SameParameterValue")
    public static MethodTest create(String expression, ClassInfo target) {
        MethodTest methodTest = new MethodTest();

        methodTest.setExpressions(new String[]{expression});
        methodTest.setTarget(target);
        methodTest.setOutputExpressions(new String[]{"@p0"});
        methodTest._ErrorMessages = new String[1];
        methodTest._ActualOutputs = new Object[1];

        return methodTest;
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param expression       Field or property name separated by '.'
     * @param target           Target class in which method reside
     * @param outputExpression Expression e.g. field, method or property to get Expected output of method
     * @return MethodTest object
     */
    @SuppressWarnings("SameParameterValue")
    public static MethodTest createWithOutputExpression(String expression, ClassInfo target, String outputExpression) {
        MethodTest methodTest = new MethodTest();

        methodTest.setTarget(target);
        methodTest.setExpressions(new String[]{expression});
        methodTest.setOutputExpressions(new String[]{outputExpression});
        methodTest._ErrorMessages = new String[1];
        methodTest._ActualOutputs = new Object[1];

        return methodTest;
    }

    /**
     * Create MethodTest instance usually used to test method with specific class return type
     *
     * @param expressions     Fields or properties name separated by '.'
     * @param target          Target class in which method reside
     * @param expectedOutputs Expected output of expressions
     * @return MethodTest object
     */
    public static MethodTest create(String[] expressions, ClassInfo target, Object[] expectedOutputs) {
        MethodTest methodTest = new MethodTest();

        methodTest.setExpressions(expressions);
        methodTest.setTarget(target);
        methodTest.setExpectedOutputs(expectedOutputs);
        methodTest._ErrorMessages = new String[expectedOutputs.length];
        methodTest._ActualOutputs = new Object[expectedOutputs.length];

        return methodTest;
    }

    /**
     * Create MethodTest instance usually used to test method with primitive, instances or primitive collections
     * return type
     *
     * @param expressions       Field or property name separated by '.'
     * @param target            Target class in which method reside
     * @param outputExpressions Expression e.g. field, method or property to get Expected output of method
     * @return MethodTest object
     */
    public static MethodTest createWithOutputExpression(String[] expressions, ClassInfo target, String[]
            outputExpressions) {
        MethodTest methodTest = new MethodTest();

        methodTest.setTarget(target);
        methodTest.setExpressions(expressions);
        methodTest.setOutputExpressions(outputExpressions);
        methodTest._ErrorMessages = new String[outputExpressions.length];
        methodTest._ActualOutputs = new Object[outputExpressions.length];

        return methodTest;
    }

    public ClassInfo getTarget() {
        return _Target;
    }

    public void setTarget(ClassInfo value) {
        _Target = value;
    }

    public String getName() {
        if (StringUtility.isNullOrEmpty(_Name)) {
            _Name = "";
        }

        return _Name;
    }

    public void setName(String value) {

        _Name = value;
    }

    public String[] getExpressions() {
        return _Expressions;
    }

    public void setExpressions(String[] value) {
        _Expressions = value;
    }

    public void setOutputExpressions(String[] value) {
        _OutputExpressions = value;
    }

    public Object getTargetInstance() {
        return _TargetInstance;
    }

    public void setTargetInstance(Object value) {
        _TargetInstance = value;
        if (_ClassInstance == null) {
            _ClassInstance = value;
        }
    }

    public Object[] getExpectedOutputs() {
        return _ExpectedOutputs;
    }

    public void setExpectedOutputs(Object[] value) {
        _ExpectedOutputs = value;
    }

    public Object[] getActualOutputs() {
        return _ActualOutputs;
    }

    public String[] getErrorMessages() {
        return _ErrorMessages;
    }

    private String[] getTargetMembers() {
        if (_Expressions != null) {
            String[] targetMembers = new String[_Expressions.length];

            for (int i = 0; i < _Expressions.length; i++) {
                targetMembers[i] = !StringUtility.isNullOrEmpty(_Expressions[i]) ? _Expressions[i] : "";
            }

            return targetMembers;
        }

        return new String[_ExpectedOutputs.length];
    }

    public MethodInfo getMethodInfo() {
        return _MethodInfo;
    }

    public void setMethodInfo(MethodInfo methodInfo) {
        _MethodInfo = methodInfo;
    }

    public Object getClassInstance() {
        return _ClassInstance;
    }

    /**
     * Set Error Message
     */
    public void setErrorMessages() {
        for (int i = 0; i < _ExpectedOutputs.length; i++) {
            String expression = "";
            if (_Expressions != null && _OutputExpressions != null) {
                expression = String.format("Testing Expression [%s] and Output Expression [%s]", _Expressions[i],
                        _OutputExpressions[i]);
            }

            String targetMember = StringUtility.isNullOrEmpty(getTargetMembers()[i]) ? "" : getTargetMembers()[i];
            _ErrorMessages[i] = String.format("[%s class [%s] - %s Expected %s value: %s != Actual %s value: %s]",
                    getName(),
                    getTarget().getType().getName(),
                    expression,
                    targetMember, SerializeUtility.serialize(getExpectedOutputs()[i]),
                    targetMember, SerializeUtility.serialize(getActualOutputs()[i]));
        }

    }

    /**
     * Set auto test actual output
     */
    public void setActualOutputs() {
        String[] expressions = _Expressions;
        Object targetInstance = _TargetInstance;
        if (_OutputExpressions != null) {
            setExpressions(_OutputExpressions);
            setTargetInstance(_ClassInstance);
            setExpectedOutputs(ExpressionUtility.resolveExpressionValue(this));
            setExpressions(expressions);
            setTargetInstance(targetInstance);
        }

        MethodTestUtility.setTestActualOutput(this, ExpressionUtility.resolveExpressionValue(this));
        setErrorMessages();
    }

    /**
     * Verify method expected output equals to actual output
     */
    public void verify() {
        verify("");
    }

    /**
     * Test method and set actual output
     */
    public void test() {
        setActualOutputs();
    }

    /**
     * Verify method expected output equals to actual output
     *
     * @param message Optional description for test
     */
    public void verify(String message) {
        for (int i = 0; i < _ExpectedOutputs.length; i++) {
            if (!SerializeUtility.serialize(getExpectedOutputs()[i]).replaceAll("\\\\u", "\\u").equals(
                    SerializeUtility.serialize(getActualOutputs()[i]).replaceAll("\\\\u", "\\u"))) {

                String methodName = StringUtility.isNullOrEmpty(message) ? "" :
                        String.format("Testing Method: %s", message);

                throw new NoMatchedException(String.format("%s %s", methodName, getErrorMessages()[i]));
            }
        }
    }

    /**
     * Test and verify
     */
    public void testAndVerify() {
        setActualOutputs();
        verify();
    }
}


