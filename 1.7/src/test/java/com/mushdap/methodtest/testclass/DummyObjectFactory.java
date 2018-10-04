package com.mushdap.methodtest.testclass;

import com.mushdap.methodtest.exception.MethodNotFoundException;
import com.mushdap.methodtest.exception.NoMatchedException;
import com.mushdap.methodtest.methodtester.Tester;
import com.mushdap.methodtest.model.MethodInfo;
import com.mushdap.methodtest.model.MethodTest;

@SuppressWarnings("unused")
public class DummyObjectFactory {

    public MethodNotFoundException createMethodNoFoundException() {
        return new MethodNotFoundException("", "");
    }

    public NoMatchedException createNoMatchedException() {
        return new NoMatchedException("");
    }

    public Tester createTester() {
        return new Tester();
    }

    public MethodInfo createMethodInfo() {
        return MethodInfo.createWithNullParameters("", null);
    }

    public MethodTest createMethodTest() {
        return new MethodTest();
    }
}
