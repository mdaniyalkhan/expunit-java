package com.mushdap.methodtest.utility;

import com.mushdap.methodtest.model.MethodTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SerializeUtilityTest {
    @Test
    public void serialize() {
        // Act
        MethodTest methodTest = new MethodTest();
        methodTest.setExpressions(new String[]{"Test"});
        String serialize = SerializeUtility.serialize(methodTest);

        // Assert
        assertThat("{\"_Expressions\":[\"Test\"]}").isEqualTo(serialize);
    }
}