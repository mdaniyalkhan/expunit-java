package com.mushdap.methodtest.testclass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Dummy MethodTest Class
 */
@SuppressWarnings("All")
public class DummyTestClass {

    private transient static Class _DummyClassType = DummyTestClass.class;
    int number1;
    int number2;
    private transient Class _Type;
    private transient DummyTestClass[] _DummyTestClassArray;
    private Exception _exception;
    private String[] _DummyTestStringArray;
    private Double[] _doubleArray;
    private int[] _intArray;
    private Float[] _floatArray;
    private short[] _shortArray;
    private Byte[] _byteArray;
    private char[] _charArray;
    private Long[] _longArray;
    private Boolean _boolArray;
    private transient List<DummyTestClass> _DummyTestClassList;
    private List<Long> _DummyLongList;
    private transient Map<String, List<DummyTestClass>> _DummyMapListField;
    private Map<String, String> _DummyMapStringField;
    private String _message = null;
    private String _message2 = null;

    public static String empty() {
        return "";
    }

    public static Class getDummyClassType() {
        return _DummyClassType;
    }

    private static double getSquareRoot(double number) {
        return Math.sqrt(number);
    }

    public void setLongList(List<Long> _DummyLongList) {
        this._DummyLongList = _DummyLongList;
    }

    public String[] getDummyTestStringArray() {
        return _DummyTestStringArray;
    }

    public void setDoubleArray(Double[] _DummyTestDoubleArray) {
        this._doubleArray = _DummyTestDoubleArray;
    }

    public Exception getException() {
        return _exception;
    }

    public Class getType() {
        return _Type;
    }

    public void setType(Class value) {
        _Type = value;
    }

    public void setIntArray(int[] _intArray) {
        this._intArray = _intArray;
    }

    public void setFloatArray(Float[] _floatArray) {
        this._floatArray = _floatArray;
    }

    public void setShortArray(short[] _shortArray) {
        this._shortArray = _shortArray;
    }

    public void setByteArray(Byte[] _byteArray) {
        this._byteArray = _byteArray;
    }

    public void setCharArray(char[] charArray) {
        this._charArray = charArray;
    }

    public void setLongArray(Long[] longArray) {
        this._longArray = longArray;
    }

    public void setBoolArray(Boolean boolArray) {
        this._boolArray = boolArray;
    }

    public int returnZero() {
        return 0;
    }

    public String getMessage() {
        return _message;
    }

    public void setMessage(String value) {
        _message = value + value;
    }

    public boolean isMessageNotNull() {
        return _message != null;
    }

    public boolean isMessageNull() {
        return _message == null;
    }

    public boolean charAt1() {
        return _message.charAt(1) == '1';
    }

    public String message1plusMessage2() {
        return _message == null ? _message2 : _message + _message2;
    }

    public String toString() {
        return "_message=" + _message + "message2=" + _message2 + "number1=" + String.valueOf(number1);
    }

    public boolean messagesNotNull() {
        return _message != null && _message2 != null;
    }

    public boolean messagesNull() {
        return !(_message != null && _message2 != null);
    }

    public boolean equals() {
        return _message.equals(_message2);
    }

    public int multiplication() {
        return number1 * number2;
    }

    public int division() {
        return number1 / number2;
    }

    public int sum() {
        return number1 + number2;
    }

    public int difference() {
        return number1 - number2;
    }

    public int increment() {
        return ++number1;
    }

    public int decrement() {
        return --number1;
    }

    public int mod() {
        return number1 % 2;
    }

    public boolean even() {
        return mod() == 0;
    }

    public boolean notEven() {
        return !even();
    }

    public boolean odd() {
        return mod() == 1;
    }

    public int negativeNumber() {
        return number1 * -1;
    }

    public boolean less() {
        return number1 < number2;
    }

    public boolean greater() {
        return number1 > number2;
    }

    public boolean lessThanEqualTo() {
        return number1 <= number2;
    }

    public boolean greaterThanEqualTo() {
        return number1 >= number2;
    }

    public boolean notEqual() {
        return number1 != number2;
    }

    public boolean equal() {
        return number1 == number2;
    }

    public void addItem(DummyTestClass item) {
        if (_DummyTestClassList == null) {
            _DummyTestClassList = new ArrayList<DummyTestClass>();
        }

        _DummyTestClassList.add(item);
    }

    public void addItems(List<DummyTestClass> items) {
        if (_DummyTestClassList == null) {
            _DummyTestClassList = new ArrayList<DummyTestClass>();
        }

        _DummyTestClassList.addAll(items);
    }

    public void addLongItem(Long item) {
        if (_DummyLongList == null) {
            _DummyLongList = new ArrayList<Long>();
        }

        _DummyLongList.add(item);
    }

    public void addLongItems(List<Long> items) {
        if (_DummyLongList == null) {
            _DummyLongList = new ArrayList<Long>();
        }

        _DummyLongList.addAll(items);
    }

    private String returnMessage2(String message) {
        return message;
    }

    public DummyTestClass returnCurrentInstance() {
        return this;
    }

    private String returnMessage(String message) {
        return message;
    }

    private String returnMessage(String message, String message2) {

        return String.format("%s %s", message, message2);
    }

    public String methodWithListAsParameter(List<String> names) {

        return String.format("%s %s", names.get(0), names.get(1));
    }

    public DummyTestClass methodWithCustomListAsParameter(List<DummyTestClass> names) {

        return new DummyTestClass();
    }

    public DummyTestClass returnDummyTestClass(String message, DummyTestClass testClass) {
        testClass.setMessage(message);
        return testClass;
    }

    public DummyTestClass returnNewDummyClass() {
        return new DummyTestClass();
    }

    public char returnInteger() {
        return 5;
    }

    public int returnCharacter() {
        return 'a';
    }

    public static class StaticDummyClass {
        public String empty() {
            return "";
        }
    }
}
