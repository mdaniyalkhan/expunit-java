package com.mushdap.methodtest.model;

import java.util.HashMap;
import java.util.Map;

public class ClassInfo {
    private Class _type;
    private Map<String, Object> _classFields;

    private ClassInfo() {
    }

    private ClassInfo(Class type, Map<String, Object> classFields) {
        setType(type);
        setClassFields(classFields);
    }

    public static ClassInfo create(Class type) {
        return new ClassInfo(type, new HashMap<>());
    }

    public static ClassInfo create(Class type, Map<String, Object> classFields) {
        return new ClassInfo(type, classFields);
    }

    public Map<String, Object> getClassFields() {
        return _classFields;
    }

    public void setClassFields(Map<String, Object> fields) {
        _classFields = fields;
    }

    public Class getType() {
        return _type;
    }

    public void setType(Class _type) {
        this._type = _type;
    }
}
