package com.mushdap.methodtest.methodtester;

import com.mushdap.methodtest.enums.Instance;
import com.mushdap.methodtest.exception.FieldNotFoundException;
import com.mushdap.methodtest.exception.MethodNotFoundException;
import com.mushdap.methodtest.exception.NoMatchedException;
import com.mushdap.methodtest.model.ClassInfo;
import com.mushdap.methodtest.model.MethodInfo;
import com.mushdap.methodtest.model.MethodTest;
import com.mushdap.methodtest.testclass.DummyObjectFactory;
import com.mushdap.methodtest.testclass.DummyTestClass;
import com.mushdap.methodtest.utility.TypeUtility;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class TesterTest {

    private static final String _Name = "MethodTest - 1";
    private Tester _tester = null;

    @Before
    public void setUp() {
        _tester = new Tester();
    }

    @Test
    public void testMethods() {

        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(
                MethodInfo.create("returnMessage",
                        new Object[]{"MethodTest-1", "MethodTest-2"},
                        MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2")));

        methodInfos.add(MethodInfo.create("returnNewDummyClass", MethodTest.create(DummyTestClass.class, Instance
                .NotNull)));

        // Assert
        _tester.testAndVerifyMethods(methodInfos);
    }

    @Test
    public void testMethodsWithFieldValue() {
        MethodInfo.create("getMessage",
                MethodTest.create(DummyTestClass.class, TypeUtility.getFieldValue("_message", DummyTestClass.class)))
                .testAndVerify();
    }

    @Test(expected = FieldNotFoundException.class)
    public void testMethodsWithFieldValue_WrongFieldName() {
        MethodInfo.create("getMessage",
                MethodTest.create(DummyTestClass.class, TypeUtility.getFieldValue("_wrong_field_name", DummyTestClass
                        .class))).testAndVerify();
    }

    @Test
    public void testSqrtMethod() {
        MethodTest.create("getSquareRoot()", DummyTestClass.class, 5.5677643628300215).testAndVerify();
    }

    @Test
    public void testMethods_WithListAsParameter() {
        MethodInfo.create("methodWithListAsParameter",
                MethodTest.create(DummyTestClass.class, "Test Output-23 Test Output-24")).testAndVerify();

        MethodInfo.create("methodWithCustomListAsParameter",
                MethodTest.create(DummyTestClass.class, Instance.NotNull)).testAndVerify();

    }

    @Test
    public void testAllMethods_WithSameExpectedOutput() {
        // Assert
        _tester.testAllMethods(DummyObjectFactory.class, Instance.NotNull);
    }

    @Test
    public void testMethod_GetterMethod() {

        // Arrange
        MethodInfo methodInfo = MethodInfo.create("returnCurrentInstance",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "this"));

        // Assert
        methodInfo.testAndVerify();
    }

    @Test
    public void testMethod_SetterMethod() {

        // Arrange
        MethodInfo methodInfo = MethodInfo.create("setMessage",
                MethodTest.createWithOutputExpression("_message",
                        DummyTestClass.class, "@p0 + @p0"));

        // Assert
        methodInfo.testAndVerify();
    }

    @Test
    public void testMethod() {

        // Arrange
        MethodInfo methodInfo = MethodInfo.create("returnMessage", new Object[]{"MethodTest-1", "MethodTest-2"},
                MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2"));

        MethodInfo.create("getException",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_exception")).testAndVerify();

        // Assert
        methodInfo.testAndVerify();
    }

    @Test
    public void testMethods_StaticMethods() {
        // Arrange
        MethodInfo emptyMethod =
                MethodInfo.create("empty", MethodTest.create(DummyTestClass.class, ""));

        // Assert
        emptyMethod.testAndVerify();
    }

    @Test
    public void testMethods_StaticClass() {

        // Arrange
        MethodInfo emptyMethod =
                MethodInfo.create("empty", MethodTest.create(ClassInfo.create(DummyTestClass.StaticDummyClass.class),
                        ""));

        // Assert
        emptyMethod.testAndVerify();
    }

    @Test
    public void testMethods_UsingExpressions() {
        // Arrange
        ArrayList<MethodInfo> methods = new ArrayList<>();

        methods.add(MethodInfo.create("getType",
                MethodTest.create("SimpleName", DummyTestClass.class, "DummyTestClass")));
        methods.add(MethodInfo.create("getType",
                MethodTest.create("DeclaredFields[0].Name", DummyTestClass.class, "_DummyClassType")));

        // Assert
        _tester.testAndVerifyMethods(methods);

        //Or

        MethodInfo.create("getType",
                MethodTest.create(new String[]{"SimpleName", "DeclaredFields.Name"}, DummyTestClass.class,
                        new Object[]{"DummyTestClass", "_DummyClassType"})).testAndVerify();
    }

    @Test
    public void testMethodReturningSpecificClass() {
        MethodInfo.create("getType",
                MethodTest.create(DummyTestClass.class, "com.mushdap.methodtest.testclass.DummyTestClass"))
                .testAndVerify();

        MethodInfo.create("getType",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_Type")).testAndVerify();
    }

    @Test
    public void testMethods_UsingOutputExpression() {
        // Arrange
        ArrayList<MethodInfo> methods = new ArrayList<>();

        methods.add(MethodInfo.create("getMessage",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message")));

        methods.add(MethodInfo.create("getMessage",
                MethodTest.createWithOutputExpression("length", DummyTestClass.class, "_message.length")));

        methods.add(MethodInfo.create("isMessageNotNull",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message != null")));

        methods.add(MethodInfo.create("isMessageNull",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message == null")));

        methods.add(MethodInfo.create("message1plusMessage2",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message == null ? _message2 : _message " +
                        "+ _message2")));

        methods.add(MethodInfo.create("message1plusMessage2",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message + _message2")));

        methods.add(MethodInfo.create("messagesNotNull",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message != null && _message2 != null")));

        methods.add(MethodInfo.create("messagesNull",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "!(_message != null && _message2 != null)" +
                        "")));

        methods.add(MethodInfo.create("equals",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message.equals(_message2)")));


        methods.add(MethodInfo.create("multiplication",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 * number2")));

        methods.add(MethodInfo.create("division",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 / number2")));

        methods.add(MethodInfo.create("sum",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 + number2")));

        methods.add(MethodInfo.create("difference",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 - number2")));

        methods.add(MethodInfo.create("mod",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 % 2")));

        methods.add(MethodInfo.create("even",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "mod % 2 == 0")));

        methods.add(MethodInfo.create("notEven",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "!even")));

        methods.add(MethodInfo.create("odd",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "mod % 2 == 1")));

        methods.add(MethodInfo.create("less",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 < number2")));

        methods.add(MethodInfo.create("negativeNumber",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 * -1")));

        methods.add(MethodInfo.create("greater",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 > number2")));

        methods.add(MethodInfo.create("increment",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "@f(number1) + 1")));

        methods.add(MethodInfo.create("decrement",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "@f(number1) - 1")));

        methods.add(MethodInfo.create("lessThanEqualTo",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 <= number2")));

        methods.add(MethodInfo.create("greaterThanEqualTo",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 >= number2")));

        methods.add(MethodInfo.create("notEqual",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 != number2")));

        methods.add(MethodInfo.create("equal",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 == number2")));

        ClassInfo target = ClassInfo.create(DummyTestClass.class, new HashMap<String, Object>() {{
            put("_message", "CustomMessage");
        }});

        MethodInfo methodWithCustomFieldValues = MethodInfo.create("getMessage", MethodTest.create(target,
                "CustomMessage"));
        methods.add(methodWithCustomFieldValues);

        methods.add(MethodInfo.create("getDummyTestStringArray",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_DummyTestStringArray")));

        methods.add(MethodInfo.create("returnZero",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "0")));

        // Assert
        _tester.testAndVerifyMethods(methods);

        //Or

        MethodInfo.create("getType",
                MethodTest.createWithOutputExpression(new String[]{"SimpleName", "DeclaredFields.Name"},
                        DummyTestClass.class,
                        new String[]{"_Type.SimpleName", "_Type.DeclaredFields.Name"})).testAndVerify();
    }

    @Test
    public void testMethods_WithNullParameters() {
        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(MethodInfo.createWithNullParameters("returnMessage2",
                MethodTest.create(DummyTestClass.class, null)));

        // Assert
        _tester.testAndVerifyMethods(methodInfos);
    }

    @Test
    public void testMethods_WithUserDefinedParameters() {
        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(MethodInfo.create("returnMessage2", new Object[]{"Test"},
                MethodTest.create(DummyTestClass.class, "Test")));

        // Assert
        _tester.testAndVerifyMethods(methodInfos);
    }

    @Test
    public void testListAddMethods() {
        MethodInfo.create("addItem",
                MethodTest.create("_DummyTestClassList.size", DummyTestClass.class, 26)).testAndVerify();
        MethodInfo.create("addItems",
                MethodTest.create("_DummyTestClassList.size", DummyTestClass.class, 50)).testAndVerify();
        MethodInfo.create("addLongItem",
                MethodTest.create("_DummyLongList.size", DummyTestClass.class, 26)).testAndVerify();
        MethodInfo.create("addLongItems",
                MethodTest.create("_DummyLongList.size", DummyTestClass.class, 50)).testAndVerify();

        TypeUtility.INITIALIZE_INSTANCE_COLLECTION_FIELDS = false;
        MethodInfo.create("addItem",
                MethodTest.create("_DummyTestClassList.size", DummyTestClass.class, 1)).testAndVerify();
        MethodInfo.create("addItems",
                MethodTest.create("_DummyTestClassList.size", DummyTestClass.class, 25)).testAndVerify();
        MethodInfo.create("addLongItem",
                MethodTest.create("_DummyLongList.size", DummyTestClass.class, 1)).testAndVerify();
        MethodInfo.create("addLongItems",
                MethodTest.create("_DummyLongList.size", DummyTestClass.class, 25)).testAndVerify();
    }

    @Test
    public void testStaticMethod_WithDefaultParameters_UsingExpressions() {
        // Arrange

        MethodTest staticGetter = MethodTest.create("DummyClassType.SimpleName", DummyTestClass.class,
                "DummyTestClass");

        MethodTest staticField = MethodTest.create("_DummyClassType.SimpleName", DummyTestClass.class,
                "DummyTestClass");

        MethodTest staticMethod = MethodTest.create("getDummyClassType.SimpleName", DummyTestClass.class,
                "DummyTestClass");

        // Assert

        staticGetter.testAndVerify();
        staticField.testAndVerify();
        staticMethod.testAndVerify();
    }

    @Test(expected = MethodNotFoundException.class)
    public void testMethods_WithWrongMethod() {

        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(
                MethodInfo.create("WrongMethod"
                        , MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2")));

        // Assert
        _tester.testAndVerifyMethods(methodInfos);
    }

    @Test(expected = NoMatchedException.class)
    public void testMethods_WithWrongExpectedOuptut() {

        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(
                MethodInfo.create("returnMessage"
                        , MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2")));

        // Assert
        _tester.testAndVerifyMethods(methodInfos);
    }

    @Test
    public void testMethods_AddMethodNamesToBeSkipped() {

        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(MethodInfo.create("returnMessage", new Object[]{"MethodTest-1", "MethodTest-2"},
                MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2")));

        methodInfos.add(MethodInfo.create("returnNewDummyClass", MethodTest.create(DummyTestClass.class, Instance
                .Null)));

        _tester.addMethodNamesToBeSkipped(new String[]{"returnNewDummyClass"});

        // Assert
        _tester.testAndVerifyMethods(methodInfos);
    }

    @Test
    public void testCharactersArray() {
        MethodInfo.create("charAt1",
                MethodTest.createWithOutputExpression(
                        ClassInfo.create(DummyTestClass.class, new HashMap<String, Object>() {{
                            put("_message", "012345789");
                        }}),
                        "_message.charAt(1) == '1'")).testAndVerify();
    }

    @Test
    public void testArrays() {
        MethodInfo.create("setDoubleArray", MethodTest.create("_doubleArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setIntArray", MethodTest.create("_intArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setShortArray", MethodTest.create("_shortArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setByteArray", MethodTest.create("_byteArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setCharArray", MethodTest.create("_charArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setFloatArray", MethodTest.create("_floatArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setLongArray", MethodTest.create("_longArray", DummyTestClass.class)).testAndVerify();
        MethodInfo.create("setBoolArray", MethodTest.create("_boolArray", DummyTestClass.class)).testAndVerify();
    }

    @Test
    public void testToString() {
        MethodInfo.create("toString",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "'_message=' + _message + 'message2=' + " +
                        "_message2 + " +
                        "'number1=' + String.valueOf(number1)")).testAndVerify();

    }

    @Test
    public void setAutoTestTargetInstance() {

        // Arrange
        ArrayList<MethodInfo> methodInfos = new ArrayList<>();

        methodInfos.add(MethodInfo.create("getName", MethodTest.create(ClassInfo.create(MethodTest.class), _Name)));

        // Assert
        assertThat(methodInfos.get(0).getMethodTest().getTargetInstance()).isNotNull();
    }

    @Test
    public void testMethods_UnMatchedReturnTypes() {
        MethodInfo.create("returnInteger",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "5")).testAndVerify();

        MethodInfo.create("returnCharacter",
                MethodTest.create(DummyTestClass.class, 'a')).testAndVerify();
    }
}