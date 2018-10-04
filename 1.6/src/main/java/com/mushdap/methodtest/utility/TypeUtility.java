package com.mushdap.methodtest.utility;

import com.google.common.collect.ObjectArrays;
import com.mushdap.methodtest.exception.EmptyException;
import com.mushdap.methodtest.exception.FieldNotFoundException;
import org.objenesis.ObjenesisStd;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;

/**
 * Utility to extend Java Reflection API Type related operations
 */
@SuppressWarnings({"WeakerAccess", "UnnecessaryBoxing", "unchecked"})
public class TypeUtility {

    public static boolean INITIALIZE_INSTANCE_COLLECTION_FIELDS = true;
    public static Map<Class, Object> USER_DEFINE_TYPES_VALUE = new HashMap<Class, Object>();

    /**
     * Get Collection for parameterized Type
     *
     * @param type parameterized Type
     * @return return collection for parameterized Type
     */
    public static Object getValue(ParameterizedType type, int fieldIndex) {
        if (type.getActualTypeArguments().length == 1) {
            Type argType = type.getActualTypeArguments()[0];
            List list = new ArrayList();
            Object[] arrayObjects = (Object[]) getArrayObjects(fieldIndex, argType);
            Collections.addAll(list, arrayObjects);
            return list;
        } else if (type.getActualTypeArguments().length == 2) {
            return new HashMap();
        }

        return null;
    }

    /**
     * Generate value for primitive types
     *
     * @param parameterName Parameter name
     * @param type          Parameter type
     * @return default value
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static Object getValue(String parameterName, Class type, int fieldIndex) {
        if (type == null) {
            return null;
        }

        if (USER_DEFINE_TYPES_VALUE.containsKey(type)) {
            return USER_DEFINE_TYPES_VALUE.get(type);
        }

        parameterName = parameterName.toLowerCase();

        if (type == Integer.class) {
            return Integer.valueOf(2 + fieldIndex);
        }
        if (type == Long.class) {
            return Long.valueOf(3 + fieldIndex);
        }
        if (type == Double.class) {
            return Double.valueOf(4.0 + fieldIndex);
        }
        if (type == Float.class) {
            return Float.valueOf(5 + fieldIndex);
        }
        if (type == Short.class) {
            return Short.valueOf(String.valueOf(6 + fieldIndex));
        }
        if (type == Byte.class) {
            return Byte.valueOf(String.valueOf(1 + fieldIndex));
        }
        if (type == int.class) {
            return 7 + fieldIndex;
        }
        if (type == long.class) {
            return 8L + fieldIndex;
        }
        if (type == double.class) {
            return 9.0D + fieldIndex;
        }
        if (type == float.class) {
            return 10F + fieldIndex;
        }
        if (type == short.class) {
            return (short) (11 + fieldIndex);
        }
        if (type == byte.class) {
            return (byte) (12 + fieldIndex);
        }

        if (type == BigInteger.class) {
            return BigInteger.valueOf(1000 + fieldIndex);
        }

        if (type == char.class) {
            return (char) fieldIndex;
        }

        if (type == Character.class) {
            return Character.valueOf((char) fieldIndex);
        }

        if (type == BigDecimal.class) {
            return BigDecimal.valueOf(2000.0 + fieldIndex);
        }

        if (type == Date.class) {
            return getDate(fieldIndex).getTime();
        }

        if (type == XMLGregorianCalendar.class) {
            try {
                return DatatypeFactory.newInstance().newXMLGregorianCalendar(getDate(fieldIndex));
            } catch (DatatypeConfigurationException e) {
                e.printStackTrace();
            }
        }

        if (parameterName.endsWith("id") &&
                type == Object.class) {
            return 1 + fieldIndex;
        }

        if ((parameterName.endsWith("type") ||
                parameterName.endsWith("flag")) &&
                type == String.class) {
            return String.valueOf(1 + fieldIndex);
        }

        if (parameterName.contains("xml") && type == String.class) {
            return "<AutomatedUnitTest>" + fieldIndex + "</AutomatedUnitTest>";
        }

        if (parameterName.endsWith("culture") && type == String.class) {
            return "en-US";
        }

        if (type == String.class) {
            return "Automated MethodTest FieldIndex = " + fieldIndex;
        }

        if (type == boolean.class) {
            return fieldIndex % 2 == 0;
        }

        if (type == Boolean.class) {
            Boolean.valueOf(fieldIndex % 2 == 0);
        }

        if (type == URI.class) {
            try {
                return new URI("https://www.google.com.pk/FieldIndex=" + fieldIndex);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }

        if (type.isAssignableFrom(Exception.class)) {
            return new EmptyException(parameterName, fieldIndex);
        }

        if (type.isArray() && type.getComponentType() != Class.class) {
            return getArrayObjects(fieldIndex, type.getComponentType());
        }

        return null;
    }

    /**
     * Generate value for primitive types
     *
     * @param type Parameter type
     * @return default value
     */
    public static Object getValue(Class type) {
        return getValue("", type);
    }

    /**
     * Generate default value for primitive types
     *
     * @param type Parameter type
     * @return default value
     */
    public static Object getDefaultValue(Class type, int fieldIndex) {
        if (type == null) {
            return null;
        }

        if (type == int.class) {
            return 0;
        }
        if (type == long.class) {
            return 0L;
        }
        if (type == double.class) {
            return 0.0D;
        }
        if (type == float.class) {
            return 0.0F;
        }
        if (type == short.class) {
            return (short) 0;
        }
        if (type == byte.class) {
            return (byte) 0;
        }

        if (type == char.class) {
            return '\u0000';
        }

        if (type == boolean.class) {
            return fieldIndex % 3 == 0;
        }

        return null;
    }

    /**
     * Get Field value for primitive types
     *
     * @param type Parameter type
     * @return default value
     */
    public static Object getFieldValue(String fieldName, Class type) {
        Field field = getFieldInfoByName(type, fieldName);
        if (field != null) {
            return getValue(fieldName, field.getType(), getFieldIndexByName(type, fieldName));
        }

        throw new FieldNotFoundException(fieldName, type.getCanonicalName());
    }

    /**
     * Generate default value for primitive types
     *
     * @param parameterName Parameter name
     * @param type          Parameter type
     * @return default value
     */
    @SuppressWarnings("SameParameterValue")
    public static Object getValue(String parameterName, Class type) {
        return getValue(parameterName, type, 0);
    }

    /**
     * Create instance of specific type
     *
     * @param type   Class type
     * @param fields Custom field values
     * @return Instance object
     */
    public static Object createInstance(Class type, Map<String, Object> fields) {
        return createInstance(type, 1, fields);
    }

    /**
     * Create instance of specific type
     *
     * @param type Class type
     * @return Instance object
     */
    public static Object createInstance(Class type) {
        return createInstance(type, 1, new HashMap<String, Object>());
    }

    /**
     * Create instance of specific type
     *
     * @param type                                Class type
     * @param levelInitializeUninitializedMembers Level to initialize nested properties and fields
     * @return Instance object
     */
    @SuppressWarnings("SameParameterValue")
    public static Object createInstance(Class type, int levelInitializeUninitializedMembers) {
        return createInstance(type, levelInitializeUninitializedMembers, new HashMap<String, Object>());
    }

    /**
     * Create instance of specific type
     *
     * @param type                                Class type
     * @param levelInitializeUninitializedMembers Level to initialize nested properties and fields
     * @param fields                              Custom Field Values
     * @return Instance object
     */
    public static Object createInstance(Class type, int levelInitializeUninitializedMembers, Map<String, Object>
            fields) {
        Object instance = null;
        if (Modifier.isInterface(type.getModifiers()) || Modifier.isAbstract(type.getModifiers())) {
            instance = getMock(type);
        }

        if (instance == null) {
            instance = TypeUtility.getValue(type.getName(), type);
        }

        if (instance == null) {
            instance = createInstanceOfTypeHavingDefaultConstructor(type);
        }

        if (instance == null) {
            instance = createUninitializedObject(type);
        }

        if (instance != null && levelInitializeUninitializedMembers > 0) {
            try {
                levelInitializeUninitializedMembers = levelInitializeUninitializedMembers - 1;
                initializeUninitializedFields(type, instance, levelInitializeUninitializedMembers, fields);
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }

        return instance;
    }

    /**
     * Initialize uninitialized fields
     *
     * @param type                               Class type
     * @param instance                           instance of class type
     * @param levelInitializeUninitializedFields Level to initialize nested fields
     * @param customFieldValues                  Custom Field Values
     */
    public static void initializeUninitializedFields(Class type, Object instance, int
            levelInitializeUninitializedFields, Map<String, Object>
            customFieldValues) {
        Field[] fields = getFields(type);
        for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
            Field fieldInfo = fields[i];
            if (Modifier.isStatic(fieldInfo.getModifiers())) {
                continue;
            }

            Class typeInfo = fieldInfo.getType();
            fieldInfo.setAccessible(true);
            try {
                if (customFieldValues.containsKey(fieldInfo.getName())) {
                    fieldInfo.set(instance, customFieldValues.get(fieldInfo.getName()));
                    continue;
                }
                if (typeInfo != void.class) {
                    if (typeInfo == Class.class) {
                        fieldInfo.set(instance, instance.getClass());
                        continue;
                    }

                    Object value = getObject(fieldInfo.getName(), typeInfo, fieldInfo.getGenericType(), i);

                    if (value != null) {
                        fieldInfo.set(instance, value);
                    } else if (!isACollection(typeInfo)) {
                        fieldInfo.set(instance, createInstance(typeInfo, levelInitializeUninitializedFields,
                                customFieldValues));
                    }
                }
            } catch (Exception exp) {
                System.out.println(exp.getMessage());
            }
        }
    }

    /**
     * Is Object instance of collection
     *
     * @param object Object
     * @return True if object is instance of collection
     */
    public static boolean isACollection(Object object) {
        boolean isACollection = false;

        if (object instanceof Collection<?>) {
            isACollection = true;
        }

        if (object instanceof Map<?, ?>) {
            isACollection = true;
        }

        if (object.getClass().isArray()) {
            isACollection = true;
        }
        return isACollection;
    }

    /**
     * Is Collection assignable from type
     *
     * @param type Class type
     * @return True if Collection is assignable from type
     */
    public static boolean isACollection(Class type) {
        boolean isACollection = false;

        if (Collection.class.isAssignableFrom(type)) {
            isACollection = true;
        }

        if (Map.class.isAssignableFrom(type)) {
            isACollection = true;
        }

        if (type.isArray()) {
            isACollection = true;
        }
        return isACollection;
    }

    /**
     * Get properties setters for given type
     *
     * @param type Class type
     * @return List of setter methods
     */
    public static Method[] getPropertiesSetter(Class<com.mushdap.methodtest.model.MethodTest> type) {
        ArrayList<Method> properties = new ArrayList<Method>();
        for (Method method : type.getMethods()) {
            if (method.getName().matches("set[A-Z].*")) {
                properties.add(method);
            }
        }
        return properties.toArray(new Method[0]);
    }

    /**
     * Get property getter for given type by property Name
     *
     * @param type         Class type
     * @param propertyName Name of property
     * @return Matching property getter
     */
    public static Method getPropertyGetter(Class type, String propertyName) {
        if (!StringUtility.isNullOrEmpty(propertyName)) {
            for (Method method : type.getMethods()) {
                if (method.getName().matches("get" + propertyName)) {
                    return method;
                }
            }
        }

        return null;
    }

    /**
     * Get properties getters for given type
     *
     * @param type Class type
     * @return List of getter methods
     */
    public static Method[] getPropertiesGetter(Class<com.mushdap.methodtest.model.MethodTest> type) {
        ArrayList<Method> properties = new ArrayList<Method>();
        for (Method method : type.getMethods()) {
            if (method.getName().matches("get[A-Z].*")) {
                properties.add(method);
            }
        }
        return properties.toArray(new Method[0]);
    }

    /**
     * Get field for given type by name
     *
     * @param type Class type
     * @param name Name of field
     * @return Matching field
     */
    public static Field getFieldInfoByName(Class type, String name) {
        if (!StringUtility.isNullOrEmpty(name)) {
            for (Field fieldInfo : findFieldsByName(type, name)) {
                if (fieldInfo.getName().equals(name)) {
                    return fieldInfo;
                }

            }
        }

        return null;
    }

    /**
     * Get field for given type by name
     *
     * @param type Class type
     * @param name Name of field
     * @return Matching field
     */
    public static int getFieldIndexByName(Class type, String name) {
        if (!StringUtility.isNullOrEmpty(name)) {
            Field[] fields = findFieldsByName(type, name);
            for (int i = 0, fieldsLength = fields.length; i < fieldsLength; i++) {
                Field fieldInfo = fields[i];
                if (fieldInfo.getName().equals(name)) {
                    return i;
                }

            }
        }

        return -1;
    }

    /**
     * Get First Object in object collection
     *
     * @param object object collection
     * @return First object in collection
     */
    public static Object getFirstObjectFromCollection(Object object) {
        if (isACollection(object)) {
            if (object.getClass().isArray()) {
                Object[] array = (Object[]) object;
                if (array.length > 0) {
                    return array[0];
                }
            } else {
                List<?> list = (List<?>) object;
                if (list.size() > 0) {
                    return list.get(0);
                }
            }
        }

        return null;
    }

    /**
     * Get fields for given type
     *
     * @param type Class type
     * @return List of fields
     */
    public static Field[] getFields(Class<?> type) {
        Field[] result = type.getDeclaredFields();

        Class<?> parentClass = type.getSuperclass();
        if (parentClass != null) {
            Field[] parentClassFields = getFields(parentClass);
            result = ObjectArrays.concat(result, parentClassFields, Field.class);
        }

        return result;
    }

    /**
     * Get fields for given type
     *
     * @param type Class type
     * @return List of fields
     */
    public static Field[] findFieldsByName(Class<?> type, String fieldName) {
        Field[] result = type.getDeclaredFields();

        Class<?> parentClass = type.getSuperclass();
        if (parentClass != null) {
            for (Field fieldInfo : result) {
                if (fieldInfo.getName().equals(fieldName)) {
                    return result;
                }
            }

            Field[] parentClassFields = findFieldsByName(parentClass, fieldName);
            result = ObjectArrays.concat(result, parentClassFields, Field.class);
        }

        return result;
    }

    /**
     * Is Public Default Constructor Exist
     */
    static Constructor<?> getPublicDefaultConstructor(Class type) {
        for (Constructor<?> constructor : type.getConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                constructor.setAccessible(true);
                return constructor;
            }
        }

        return null;
    }

    /**
     * Is Private Default Constructor Exist
     */
    static Constructor<?> getPrivateDefaultConstructor(Class type) {
        for (Constructor<?> constructor : type.getDeclaredConstructors()) {
            if (constructor.getParameterTypes().length == 0) {
                constructor.setAccessible(true);
                return constructor;
            }
        }
        return null;
    }

    @SuppressWarnings("ConstantConditions")
    static Object createInstanceOfTypeHavingDefaultConstructor(Class type) {
        Object instance = null;
        try {
            if (getPublicDefaultConstructor(type) != null) {
                instance = getPublicDefaultConstructor(type).newInstance();
            } else if (getPrivateDefaultConstructor(type) != null) {
                instance = getPrivateDefaultConstructor(type).newInstance();
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        return instance;
    }

    @SuppressWarnings("unchecked")
    static Object createUninitializedObject(Class type) {
        Object instance = null;
        try {
            instance = new ObjenesisStd().newInstance(type);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        return instance;
    }

    static Class<?> getUnBoxType(Class<?> boxType) {
        if (boxType == null) {
            return null;
        }
        if (boxType == Byte.class) {
            return byte.class;
        } else if (boxType == Short.class) {
            return short.class;
        } else if (boxType == Double.class) {
            return double.class;
        } else if (boxType == Integer.class) {
            return int.class;
        } else if (boxType == Long.class) {
            return long.class;
        } else if (boxType == Boolean.class) {
            return boolean.class;
        } else if (boxType == Float.class) {
            return float.class;
        } else if (boxType == Character.class) {
            return char.class;
        } else {
            return boxType;
        }
    }

    private static Object getArrayObjects(int fieldIndex, Type argType) {
        Object objects = Array.newInstance((Class<?>) argType, 25);
        for (int index = 0; index < 25; index++) {
            int value = index + 1 + fieldIndex;
            if (argType == String.class) {
                Array.set(objects, index, String.format("Test Output-%s", value));
            } else {
                Class type = (Class) argType;
                if (getUnBoxType(type) == int.class) {
                    Array.set(objects, index, value);
                } else if (getUnBoxType(type) == double.class) {
                    Array.set(objects, index, (double) value);
                } else if (getUnBoxType(type) == short.class) {
                    Array.set(objects, index, (short) value);
                } else if (getUnBoxType(type) == float.class) {
                    Array.set(objects, index, (float) value);
                } else if (getUnBoxType(type) == byte.class) {
                    Array.set(objects, index, (byte) value);
                } else if (getUnBoxType(type) == long.class) {
                    Array.set(objects, index, (long) value);
                } else if (getUnBoxType(type) == boolean.class) {
                    Array.set(objects, index, (value % 2) == 0);
                } else if (argType == BigInteger.class) {
                    Array.set(objects, index, BigInteger.valueOf(value));
                } else if (argType == BigDecimal.class) {
                    Array.set(objects, index, BigDecimal.valueOf(value));
                } else if (argType == Date.class) {
                    Array.set(objects, index, getDate(fieldIndex).getTime());
                } else if (argType == XMLGregorianCalendar.class) {
                    try {
                        Array.set(objects, index, DatatypeFactory.newInstance().
                                newXMLGregorianCalendar(getDate(fieldIndex)));
                    } catch (DatatypeConfigurationException e) {
                        e.printStackTrace();
                    }
                } else if (argType == char.class) {
                    Array.set(objects, index, (char) fieldIndex);
                } else if (argType == Character.class) {
                    Array.set(objects, index, Character.valueOf((char) fieldIndex));
                } else {
                    Array.set(objects, index, TypeUtility.createInstance(type, 0));
                }
            }
        }

        return objects;
    }

    private static Object getObject(String name, Class typeInfo, Type genericType, int fieldIndex) {
        Object value = null;
        if (!isACollection(typeInfo) || typeInfo.isArray()) {
            value = TypeUtility.getValue(name, typeInfo, fieldIndex);
        } else if (INITIALIZE_INSTANCE_COLLECTION_FIELDS && genericType instanceof ParameterizedType) {
            value = TypeUtility.getValue((ParameterizedType) genericType, fieldIndex);
        }
        return value;
    }

    private static GregorianCalendar getDate(int fieldIndex) {
        return new GregorianCalendar(Math.min(+999999999, 2000 + fieldIndex), Math.min(12, 1 + fieldIndex), Math.min
                (27, 1 + fieldIndex));
    }

    private static Object getMock(Class type) {
        Object mock = null;
        try {
            mock = mock(type);
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        return mock;
    }

    /**
     * Cast Value With Respect To String
     *
     * @param obj Expression Value
     * @return Casted value
     */
    @SuppressWarnings({"RedundantCast", "unused"})
    public static String typeToString(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            if (getUnBoxType(obj.getClass()) == char.class) {
                obj = "'" + "\\u" + Integer.toHexString((Character) obj | 0x10000).substring(1) + "'";
            } else if (getUnBoxType(obj.getClass()) == String.class) {
                obj = "'" + obj.toString().replaceAll("'", "''") + "'";
            } else if (getUnBoxType(obj.getClass()) == long.class) {
                obj = obj.toString() + "L";
            } else if (getUnBoxType(obj.getClass()) == double.class) {
                obj = obj.toString() + "D";
            } else if (getUnBoxType(obj.getClass()) == float.class) {
                obj = obj.toString() + "F";
            } else {
                obj = obj.toString();
            }
        } catch (Exception ignored) {

        }

        return obj.toString();
    }

    public static Object CastValueWithRespectToType(Object obj, Class type) {
        if (type == null) {
            return obj;
        }

        try {
            if (getUnBoxType(obj.getClass()) == char.class && getUnBoxType(type) == int.class) {
                obj = Integer.valueOf((Character) obj);
            } else if (getUnBoxType(obj.getClass()) == char.class && getUnBoxType(type) == String.class) {
                obj = typeToString(obj);
            } else if (getUnBoxType(obj.getClass()) == int.class && getUnBoxType(type) == char.class) {
                obj = (char) ((Integer) obj).intValue();
            } else if (getUnBoxType(obj.getClass()) == int.class && getUnBoxType(type) == double.class) {
                obj = Double.valueOf((Integer) obj);
            } else if (getUnBoxType(obj.getClass()) == int.class && getUnBoxType(type) == float.class) {
                obj = Float.valueOf((Integer) obj);
            } else if (getUnBoxType(obj.getClass()) == long.class && getUnBoxType(type) == double.class) {
                obj = Double.valueOf((Long) obj);
            } else if (getUnBoxType(obj.getClass()) == long.class && getUnBoxType(type) == float.class) {
                obj = Float.valueOf((Long) obj);
            }
        } catch (Exception ignored) {

        }

        return obj;
    }
}


