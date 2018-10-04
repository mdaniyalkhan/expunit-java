package com.mushdap.methodtest.utility;

import com.mushdap.methodtest.model.MethodInfo;
import com.mushdap.methodtest.model.MethodTest;
import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

public class TypeUtilityTest {
    @Test
    public void getDefaultValue() {
        assertThat(TypeUtility.getValue(String.class)).isEqualTo("Automated MethodTest FieldIndex = 0");
        assertThat(TypeUtility.getValue(Integer.class)).isEqualTo(2);
        assertThat(TypeUtility.getValue(Long.class)).isEqualTo((long) 3);
        assertThat(TypeUtility.getValue(Double.class)).isEqualTo(4.0);
        assertThat(TypeUtility.getValue(Float.class)).isEqualTo((float) 5);
        assertThat(TypeUtility.getValue(Short.class)).isEqualTo((short) 6);
        assertThat(TypeUtility.getValue(Byte.class)).isEqualTo((byte) 1);
        assertThat(TypeUtility.getValue(int.class)).isEqualTo(7);
        assertThat(TypeUtility.getValue(long.class)).isEqualTo((long) 8);
        assertThat(TypeUtility.getValue(double.class)).isEqualTo((double) 9);
        assertThat(TypeUtility.getValue(float.class)).isEqualTo((float) 10);
        assertThat(TypeUtility.getValue(short.class)).isEqualTo((short) 11);
        assertThat(TypeUtility.getValue(byte.class)).isEqualTo((byte) 12);
        assertThat(TypeUtility.getValue(BigInteger.class)).isEqualTo(BigInteger.valueOf(1000));
        assertThat(TypeUtility.getValue(BigDecimal.class)).isEqualTo(BigDecimal.valueOf(2000.0));
    }

    @Test
    public void createInstanceOfTypeHavingPublicDefaultConstructor() {
        Object instance = TypeUtility.createInstanceOfTypeHavingDefaultConstructor(MethodTest.class);
        assertThat(instance).isNotNull();
    }

    @Test
    public void createUninitializedObject() throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        Object instance = TypeUtility.createUninitializedObject(MethodInfo.class);
        assertThat(instance);
        assertThat(instance.getClass().getDeclaredMethod("getMethodTest").invoke(instance)).isNull();
    }

    @Test
    public void createInstance() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Object instance = TypeUtility.createInstance(MethodInfo.class);
        assertThat(instance);
        assertThat(instance.getClass().getDeclaredMethod("getMethodTest").invoke(instance));
        assertThat(instance.getClass().getDeclaredMethod("getMethodName").invoke(instance));
    }

    @Test
    public void isACollection() {
        assertThat(TypeUtility.isACollection(new ArrayList<Integer>())).isTrue();
        assertThat(TypeUtility.isACollection(new ArrayList<MethodTest>())).isTrue();
        assertThat(TypeUtility.isACollection(new HashMap<String, String>())).isTrue();
        assertThat(TypeUtility.isACollection(new Integer[1]));
    }

    @Test
    public void isTypeACollection() {
        assertThat(TypeUtility.isACollection(ArrayList.class)).isTrue();
        assertThat(TypeUtility.isACollection(Integer[].class)).isTrue();
        assertThat(TypeUtility.isACollection(HashMap.class)).isTrue();
    }

    @Test
    public void getFields() {
        Field[] fields = TypeUtility.getFields(MethodTest.class);

        assertThat("_Name").isEqualTo(fields[0].getName());
    }

    @Test
    public void getFieldInfoByName() {
        Field field = TypeUtility.getFieldInfoByName(MethodTest.class, "_Name");
        assertThat(field).isNotNull();
        assertThat("_Name").isEqualTo(field.getName());
    }

    @Test
    @Ignore
    public void getPropertiesSetter() {
        Method[] properties = TypeUtility.getPropertiesSetter(MethodTest.class);
        assertThat("setName").isEqualTo(properties[0].getName());
    }

    @Test
    @Ignore
    public void getPropertiesGetter() {
        Method[] properties = TypeUtility.getPropertiesGetter(MethodTest.class);
        assertThat("getName").isEqualTo(properties[0].getName());
    }

    @Test
    public void getFirstObjectFromCollection() {

        // Arrange
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(1);
        integers.add(2);

        Integer[] integers2 = new Integer[]{0, 1, 2};
        String[] strings = new String[]{"A", "B", "C"};

        // Assert
        assertThat(1).isEqualTo(TypeUtility.getFirstObjectFromCollection(integers));
        assertThat(0).isEqualTo(TypeUtility.getFirstObjectFromCollection(integers2));
        assertThat("A").isEqualTo(TypeUtility.getFirstObjectFromCollection(strings));
        assertThat(TypeUtility.getFirstObjectFromCollection(new ArrayList<>())).isNull();
        assertThat(TypeUtility.getFirstObjectFromCollection(new Integer[0])).isNull();
    }

    @Test
    public void getPublicDefaultConstructor() {
        // Act
        Constructor<?> constructor = TypeUtility.getPublicDefaultConstructor(MethodTest.class);
        Constructor<?> constructorAutoTestMethod = TypeUtility.getPublicDefaultConstructor(MethodInfo.class);

        // Assert
        assertThat(constructor).isNotNull();
        assertThat(constructorAutoTestMethod).isNull();
    }

    @Test
    public void getPrivateDefaultConstructor() {
        // Act
        Constructor<?> constructor = TypeUtility.getPrivateDefaultConstructor(MethodTest.class);

        // Assert
        assertThat(constructor).isNotNull();
    }

    @Test
    public void typeToString() {
        assertThat(TypeUtility.typeToString(1)).isEqualTo("1");
        assertThat(TypeUtility.typeToString(1.0)).isEqualTo("1.0D");
        assertThat(TypeUtility.typeToString(1L)).isEqualTo("1L");
        assertThat(TypeUtility.typeToString(1D)).isEqualTo("1.0D");
        assertThat(TypeUtility.typeToString(1F)).isEqualTo("1.0F");
        assertThat(TypeUtility.typeToString("1")).isEqualTo("'1'");
        assertThat(TypeUtility.typeToString('1')).isEqualTo("'\\u0031'");
    }
}
