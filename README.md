# expunit [![CircleCI](https://circleci.com/gh/mdaniyalkhan/expunit-java/tree/master.svg?style=shield)](https://circleci.com/gh/mdaniyalkhan/expunit-java/tree/master) [![GitHub license](https://img.shields.io/badge/license-MIT-blue.svg)](https://raw.githubusercontent.com/circleci/circleci-docs/master/LICENSE)
Expression based Testing.

Library main points are following:

* Shorter and convenient ways to test any class method.
* No need to create instance of class to be tested.
* Have option to defined custom method paramter values.
* Have option to define custom method response and exception for mocking frameworks.
* Have option to skip specific method.

### Examples

Test Classes
```java
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
        return number1++;
    }

    public int decrement() {
        return number1--;
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
            _DummyTestClassList = new ArrayList<>();
        }

        _DummyTestClassList.add(item);
    }

    public void addItems(List<DummyTestClass> items) {
        if (_DummyTestClassList == null) {
            _DummyTestClassList = new ArrayList<>();
        }

        _DummyTestClassList.addAll(items);
    }

    public void addLongItem(Long item) {
        if (_DummyLongList == null) {
            _DummyLongList = new ArrayList<>();
        }

        _DummyLongList.add(item);
    }

    public void addLongItems(List<Long> items) {
        if (_DummyLongList == null) {
            _DummyLongList = new ArrayList<>();
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

    public char returnInteger(){
        return 5;
    }

    public int returnCharacter(){
        return 'a';
    }

    public static class StaticDummyClass {
        public String empty() {
            return "";
        }
    }
}
```
```java
public class DummyObjectFactory {

    public A createA() {
        return new A();
    }

    public B createB() {
        return new B();
    }
    
    public C createC() {
        return new C();
    }    
 
    public D createD() {
        return new D();
    }
}
```

Testing All Class Methods
* This method is used to test and verify proxy, web services, factory and domain classes methods
* testAllMethods([Class To Be Tested],[Expected Output of all class methods])
```java
// Assert
_tester.testAllMethods(DummyObjectFactory.class, Instance.NotNull);
```               

Testing Multiple Methods
* These methods are used to test and verify multiple methods 
* MethodInfo.create([Method Name], [Method Parameter Values], MethodTest.create([Target Class], [Expected Method Output]))
* MethodInfo.create([Method Name], MethodTest.create([Target Class], [Expected Method Output])) --Default Test Values are assigned as     method parameters
* _tester.testAndVerifyMethods([List of methods to be tested])
```java
// Arrange
ArrayList<MethodInfo> methods = new ArrayList<>();

methods.add(MethodInfo.create("returnMessage",
                new Object[]{"MethodTest-1", "MethodTest-2"},
                MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2")));

methods.add(MethodInfo.create("returnMessage", 
                                  MethodTest.create(DummyTestClass.class, "Automated MethodTest Automated MethodTest")));

methods.add(MethodInfo.create("returnNewDummyClass", MethodTest.create(DummyTestClass.class, Instance.NotNull)));

methods.add(MethodInfo.create("getMessage",
                MethodTest.create(DummyTestClass.class, TypeUtility.getFieldValue("_message", DummyTestClass.class))));

// Assert
_tester.testAndVerifyMethods(methodInfos);
```

Testing Single Method
* These methods are used to test and verify single method
* MethodInfo.create([Method Name], [Method Parameter Values], MethodTest.create([Target Class], [Expected Method Output]))
* [methodToTested].testAndVerify()
```java
// Arrange
MethodInfo method = MethodInfo.create("returnMessage", new Object[]{"MethodTest-1", "MethodTest-2"},
                                   MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2"));

// Assert
method.testAndVerify();
```

Test Static Method
* These methods are used to test and verify static method
* MethodInfo.create([Static Method Name], MethodTest.create([Target Class], [Expected Method Output]))
* [methodToTested].testAndVerify()
```java
// Arrange
MethodInfo emptyMethod = MethodInfo.create("empty", MethodTest.create(DummyTestClass.class, ""));

// Assert
emptyMethod.testAndVerify();
```

Test Nested Static Class Method
* These methods are used to test and verify static class method
* MethodInfo.create([Method Name], MethodTest.create([Static Target Class], [Expected Method Output]))
* [methodToTested].testAndVerify()
```java
// Arrange
MethodInfo emptyMethod = MethodInfo.create("empty", MethodTest.create(DummyTestClass.StaticDummyClass.class, ""));

// Assert
emptyMethod.testAndVerify();
```

Test Methods Using Expressions
* These method are used to test and verify methods using expressions
* Expressions: Any field, property, method name separated by "."
* MethodInfo.create([Method Name], MethodTest.create([Property, field or method to be test], [Target Class], [Expected Method Output]))
 [methodToTested].testAndVerify()
```java
// Arrange
ArrayList<MethodInfo> methods = new ArrayList<>();

methods.add(MethodInfo.create("getType", MethodTest.create("SimpleName", DummyTestClass.class, "DummyTestClass")));
methods.add(MethodInfo.create("getType", MethodTest.create("SimpleName", DummyTestClass.class, "DummyTestClass")));
methods.add(MethodInfo.create("getType", MethodTest.create("DeclaredFields.Name", DummyTestClass.class, "_Type")));

// Assert
_tester.testAndVerifyMethods(methods);
```

Test Methods Using Output Expressions
* These method are used to test and verify methods outputs with class other methods and fields using expressions
* Expressions: Any field, property, method name separated by "."
* OutputExpressions: Any field, property, method name separated by "."
* MethodInfo.create([Method Name], MethodTest.createWithOutputExpression([Property, field or method to be test], [Target Class], [Property, field or method to be verify]))
 [methodToTested].testAndVerify()
```java
// Arrange
ArrayList<MethodInfo> methods = new ArrayList<>();

methods.add(MethodInfo.create("getMessage", MethodTest.createWithOutputExpression(DummyTestClass.class, "_message")));
methods.add(MethodInfo.create("getMessage", MethodTest.createWithOutputExpression("length", DummyTestClass.class, "_message.length")));
methods.add(MethodInfo.create("isMessageNotNull", MethodTest.createWithOutputExpression(DummyTestClass.class, "_message != null")));
methods.add(MethodInfo.create("isMessageNull", MethodTest.createWithOutputExpression(DummyTestClass.class, "_message == null")));
methods.add(MethodInfo.create("message1plusMessage2",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message == null ? _message2 : _message + _message2")));
methods.add(MethodInfo.create("message1plusMessage2", 
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message + _message2")));
methods.add(MethodInfo.create("messagesNotNull",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_message != null && _message2 != null")));
methods.add(MethodInfo.create("messagesNull",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "!(_message != null && _message2 != null)")));
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
methods.add(MethodInfo.create("lessThanEqualTo",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 <= number2")));
methods.add(MethodInfo.create("greaterThanEqualTo",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 >= number2")));
methods.add(MethodInfo.create("notEqual",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 != number2")));
methods.add(MethodInfo.create("equal",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "number1 == number2")));

ClassInfo target = ClassInfo.create(DummyTestClass.class, new HashMap<String, Object>() {{ put("_message", "CustomMessage");}});
MethodInfo methodWithCustomFieldValues = MethodInfo.create("getMessage", MethodTest.create(target, "CustomMessage"));
methods.add(methodWithCustomFieldValues);

methods.add(MethodInfo.create("getDummyTestStringArray", 
                MethodTest.createWithOutputExpression(DummyTestClass.class, "_DummyTestStringArray")));
methods.add(MethodInfo.create("returnZero",
                MethodTest.createWithOutputExpression(DummyTestClass.class, "0")));
methods.add(MethodInfo.create("charAt1", 
                MethodTest.createWithOutputExpression(
 ClassInfo.create(DummyTestClass.class, new HashMap<String, Object>() {{put("_message", "012345789");}}),"_message.charAt(1) == '1'")))
methods.add(MethodInfo.create("toString",
                MethodTest.createWithOutputExpression(DummyTestClass.class, 
                "'_message=' + _message + 'message2=' + _message2 + "'number1=' + String.valueOf(number1)")))

//Or
MethodInfo.create("getType",
        MethodTest.createWithOutputExpression(new String[]{"SimpleName", "DeclaredFields.Name"}, DummyTestClass.class, 
                                              new String[]{"_Type.SimpleName", "_Type.DeclaredFields.Name"})).testAndVerify();
// Assert
_tester.testAndVerifyMethods(methods);
```

Test Static Method, Property Or Field Using Expressions
* These method are used to test and verify static methods, properties and fields using expressions
* Expressions: Any field, property, method name separated by "."
* MethodTest.create([Expression], [Static Target Class], [Expected Method Output]))
```java
// Arrange
MethodTest staticGetter = MethodTest.create("DummyClassType.SimpleName", DummyTestClass.class, "DummyClass");
MethodTest staticField = MethodTest.create("_DummyClassType.SimpleName", DummyTestClass.class, "DummyClass");
MethodTest staticMethod = MethodTest.create("getDummyClassType.SimpleName", DummyTestClass.class, "DummyClass");

// Assert
staticGetter.testAndVerify();
staticField.testAndVerify();
staticMethod.testAndVerify();
```

Test UnSkipped Methods
* These method are used to test and verify unskipped methods
* addMethodNamesToBeSkipped([List of method names to be skipped])
```java
// Arrange
ArrayList<MethodInfo> methods = new ArrayList<>();

methods.add(MethodInfo.create("returnMessage", new Object[]{"MethodTest-1", "MethodTest-2"},
                         MethodTest.create(DummyTestClass.class, "MethodTest-1 MethodTest-2")));

methods.add(MethodInfo.create("returnNewDummyClass", MethodTest.create(DummyTestClass.class, Instance.Null)));

_tester.addMethodNamesToBeSkipped(new String[]{"returnNewDummyClass"});

// Assert
_tester.testAndVerifyMethods(methodInfos);
```
For Method Tester Examples code [here](https://github.com/mdaniyalkhan/expunit-java/blob/master/src/test/java/com/mushdap/methodtest/methodtester/TesterTest.java).

### Configurations:
* TypeUtility.INITIALIZE_INSTANCE_COLLECTION_FIELDS: true to Generate or initialize values for class collection fields
* TypeUtility.USER_DEFINE_TYPES_VALUE:               Mapper to map custom values for specific type
### Exceptions:
* MethodNotFoundException: Throw Runtime exception if test method is not found in class
* NoMatchedException:      Throw Runtime exception if test method expected output not matched with actual output
* FieldNotFoundException:  Throw Runtime exception if test field is not found in class

### Installation

To use from maven add this snippet to the pom.xml `dependencies` section:

```xml
<dependency>
  <groupId>com.mushdap</groupId>
  <artifactId>expunit</artifactId>
  <version>1.0.0</version>
</dependency>
```

### Required dependencies

```xml
<dependency>
  <groupId>org.objenesis</groupId>
  <artifactId>objenesis</artifactId>
  <version>2.5.1</version>
</dependency>
```
```xml
<dependency>
  <groupId>com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.8.1</version>
</dependency>
```
```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-expression</artifactId>
    <version>5.0.0.RELEASE</version>
</dependency>
```
```xml
<dependency>
    <groupId>com.google.collections</groupId>
    <artifactId>google-collections</artifactId>
    <version>1.0</version>
</dependency>
```
```xml
<dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>1.10.19</version>
</dependency>
```
