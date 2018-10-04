package com.mushdap.methodtest.utility;

import com.mushdap.methodtest.model.MethodTest;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

public class MethodUtilityTest {

    @Test
    public void getMethodsByName() {
        // Act
        Method[] methods = MethodUtility.getMethodsByName(MethodTest.class, "create");
        Method[] methodsUnknown = MethodUtility.getMethodsByName(MethodTest.class, "UnknownMethod");

        // Assert
        assertThat(methods.length == 8).isTrue();
        assertThat(methodsUnknown.length == 0).isTrue();
    }

    @Test
    public void getMethodByName() {
        // Act
        Method method = MethodUtility.getMethodByName(MethodTest.class, "create");
        Method methodUnknown = MethodUtility.getMethodByName(MethodTest.class, "UnknownMethod");

        // Assert
        assertThat(method).isNotNull();
        assertThat(methodUnknown).isNull();
    }

    @Test
    public void getMethodParametersValues() {
        // Act
        int totalNumberOfFields = MethodTest.class.getDeclaredFields().length + 1;
        Object[] parametersValues =
                MethodUtility.getMethodParametersValues(MethodUtility.getMethodByName(MethodTest.class, "setName"));

        // Assert
        assertThat(TypeUtility.getValue("", String.class, totalNumberOfFields)).isEqualTo(parametersValues[0]);
    }

    @Test
    public void getMethodNullParametersValues() {
        // Act
        Object[] parametersValues = MethodUtility.getMethodNullParametersValues(MethodUtility.getMethodByName
                (MethodTest.class, "setName"));

        // Assert
        assertThat(parametersValues[0]).isNull();
    }

    @Test
    public void isMethodParametersMatched() {
        assertThat((MethodUtility.isMethodParametersMatched(new Class[]{String.class}, new Class[]{String.class})))
                .isTrue();
        assertThat(MethodUtility.isMethodParametersMatched(new Class[]{String.class}, new Class[]{Integer.class}))
                .isFalse();
    }
}
