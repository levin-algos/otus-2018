package ru.otus.algo.common;

import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;

public class ReflectionEntry {

    private Object object;

    public ReflectionEntry(Object object) {
        this.object = object;
    }

    public ReflectionEntry getField(String fieldName) {
        Field currentField = FieldUtils.getField(object.getClass(), fieldName, true);

        if (currentField == null)
            throw new IllegalStateException("no field: " + fieldName);

        try {
            Object res = currentField.get(this.object);
            return res == null? null : new ReflectionEntry(res);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIntFieldValue(String fieldName) {
        Field field = FieldUtils.getField(object.getClass(), fieldName, true);
        if (field == null)
            throw new IllegalStateException("no field: " + fieldName);
        try {
            return field.getInt(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public Object getValue(String fieldName) {
        Field currentField = FieldUtils.getField(object.getClass(), fieldName, true);

        try {
            return currentField.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
