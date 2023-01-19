package com.zz.lib.common;

import com.zz.lib.common.exception.DataSizeException;
import com.zz.lib.common.exception.DataTypeException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class CheckUtil {
    private final static String[] sizeMethodNames = {"size", "length"};
    private final static String[] sizeFieldNames = {"size", "length"};

    private CheckUtil() {
    }

    public static void assertTrue(Boolean condition) {
        assertTrue(condition, "error,assertTrue()=false.");
    }

    public static void assertTrue(Boolean condition, String message) {
        if (!condition) {
            throw new RuntimeException(message);
        }
    }

    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
    }

    //assert that the object provided is an instance of the class provided.
    public static <T> void assertIsA(Object object, Class<T> clazz, String message) {
        if (object == null) {
            throw new DataTypeException("null is not a " + clazz.getCanonicalName());
        }
        if (!clazz.isAssignableFrom(object.getClass())) {
            if (message != null) {
                throw new DataTypeException(message);
            } else {
                throw new DataTypeException("object(" + object + ") with type(" + object.getClass().getCanonicalName() + ") is not a " + clazz.getCanonicalName());
            }
        }
    }

    public static <T> void assertIsA(Object object, Class<T> clazz) {
        assertIsA(object, clazz, null);
    }

    public static void assertSize(Object container, int expectedSize, String message) {
        Class<?> clazz = container.getClass();
        Integer actualSizeValue = null;
        if(clazz.isArray()){
            actualSizeValue= Array.getLength(container);
        }else{
            Field sizeField = null;
            for (String name : sizeFieldNames) {
                try {
                    sizeField = clazz.getField(name);
                } catch (NoSuchFieldException ignore) {
                }
            }
            if (sizeField != null && (sizeField.getType() == Integer.class || sizeField.getType() == Integer.TYPE)) {
                try {
                    actualSizeValue = (Integer) sizeField.get(container);
                } catch (IllegalAccessException ignore) {
                }
            }
            Method sizeMethod = null;
            for (String name : sizeMethodNames) {
                try {
                    sizeMethod = clazz.getMethod(name);
                } catch (NoSuchMethodException ignore) {
                }
            }
            if (sizeMethod != null && (sizeMethod.getReturnType() == Integer.class || sizeMethod.getReturnType() == Integer.TYPE)) {
                try {
                    actualSizeValue = (Integer) sizeMethod.invoke(container);
                } catch (IllegalAccessException | InvocationTargetException ignore) {
                }
            }
        }
        if (actualSizeValue == null) {
            throw new DataTypeException("getting size info failed,object may be not a container.");
        } else if (actualSizeValue != expectedSize) {
            if (message != null) {
                throw new DataSizeException(message);
            } else {
                throw new DataSizeException("container size=" + actualSizeValue + ",not equal to the expected value " + expectedSize + ".");
            }
        }
    }

    public static void assertSize(Object container, int expectedSize) {
        assertSize(container, expectedSize, null);
    }
}
