package com.mushdap.methodtest.utility;

import com.mushdap.methodtest.model.ClassInfo;
import com.mushdap.methodtest.model.MethodTest;
import com.mushdap.methodtest.testclass.DummyTestClass;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodTestUtilityTest {
    @Test
    public void getParentExpressionValue() throws Exception {
        // Arrange
        MethodTest methodTest = new MethodTest();

        DummyTestClass testValue = new DummyTestClass();
        testValue.setType(DummyTestClass.class);

        methodTest.setTarget(ClassInfo.create(DummyTestClass.class));
        methodTest.setExpressions(new String[]{"Type.SimpleName"});
        methodTest.setTargetInstance(testValue);

        MethodTest methodTestArray = new MethodTest();

        methodTestArray.setTarget(ClassInfo.create(DummyTestClass.class));
        methodTestArray.setExpressions(new String[]{"Type.DeclaredFields.Name"});
        methodTestArray.setTargetInstance(testValue);

        MethodTest methodTestField = new MethodTest();

        methodTestField.setTarget(ClassInfo.create(DummyTestClass.class));
        methodTestField.setExpressions(new String[]{"_Type.SimpleName"});
        methodTestField.setTargetInstance(testValue);

        MethodTest methodTestStaticGetter = new MethodTest();

        methodTestStaticGetter.setTarget(ClassInfo.create(DummyTestClass.class));
        methodTestStaticGetter.setExpressions(new String[]{"DummyClassType.SimpleName"});

        MethodTest methodTestStaticField = new MethodTest();

        methodTestStaticField.setTarget(ClassInfo.create(DummyTestClass.class));
        methodTestStaticField.setExpressions(new String[]{"_DummyClassType.SimpleName"});

        MethodTest methodTestMethod = new MethodTest();

        methodTestMethod.setTarget(ClassInfo.create(DummyTestClass.class));
        methodTestMethod.setExpressions(new String[]{"getDummyClassType.SimpleName"});

        // Act
        Object[] baseObject = MethodTestUtility.getParentExpressionValue(methodTest);
        Object[] baseObjectArray = MethodTestUtility.getParentExpressionValue(methodTestArray);
        Object[] baseObjectField = MethodTestUtility.getParentExpressionValue(methodTestField);
        Object[] baseObjectStaticGetter = MethodTestUtility.getParentExpressionValue(methodTestStaticGetter);
        Object[] baseObjectStaticField = MethodTestUtility.getParentExpressionValue(methodTestField);
        Object[] baseObjectMethod = MethodTestUtility.getParentExpressionValue(methodTestMethod);

        // Assert
        assertThat("DummyTestClass").isEqualTo(baseObject[0]);
        assertThat("_DummyClassType").isEqualTo(baseObjectArray[0]);
        assertThat("DummyTestClass").isEqualTo(baseObjectField[0]);
        assertThat("DummyTestClass").isEqualTo(baseObjectStaticGetter[0]);
        assertThat("DummyTestClass").isEqualTo(baseObjectStaticField[0]);
        assertThat("DummyTestClass").isEqualTo(baseObjectMethod[0]);
    }
}