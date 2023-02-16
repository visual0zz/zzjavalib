package com.zz.lib.common;
import com.zz.lib.common.exception.DataCheckException;
import com.zz.lib.common.exception.DataContentException;
import com.zz.lib.common.exception.DataSizeException;
import com.zz.lib.common.exception.DataTypeException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Pattern;
public final class CheckUtil {
    private final static String[] sizeMethodNames = {"size", "length"};
    private final static String[] sizeFieldNames = {"size", "length"};
    private CheckUtil() {
    }
    public static void assertTrue(Boolean... conditions) {
        assertTrue("error,assertTrue()=false.", conditions);
    }
    public static void assertTrue(String message, Boolean... conditions) {
        for (Boolean condition : conditions) {
            if (!condition) {
                throw new DataCheckException(message);
            }
        }
    }
    public static <T> void assertEquals(T actual, T expected, String message) {
        boolean fail = false;
        if (actual != null && expected != null) {
            if (!expected.equals(actual)) {
                fail = true;
            }
        } else if (!(actual == null && expected == null)) {
            fail = true;
        }
        if (fail) {
            if (message == null)
                throw new DataContentException("assert fail,expected=" + expected + "  actual=" + actual);
            else
                throw new DataContentException(message);
        }
    }
    public static <T> void assertEquals(T actual, T expected) {
        assertEquals(actual, expected, null);
    }
    public static void assertNotNull(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
    }
    public static void assertNotNull(Object object,Object... objects) {
        if (object == null) {
            throw new NullPointerException();
        }
        for (Object o : objects) {
            if (o == null) {
                throw new NullPointerException();
            }
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
    public static void assertSize(Object container, int min, int max, String message) {
        Class<?> clazz = container.getClass();
        Integer actualSizeValue = null;
        if (clazz.isArray()) {
            actualSizeValue = Array.getLength(container);
        } else {
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
        } else if (actualSizeValue < min || actualSizeValue > max) {
            if (message != null) {
                throw new DataSizeException(message);
            } else {
                throw new DataSizeException("container size=" + actualSizeValue + ",not in [" + min + "," + max + "].");
            }
        }
    }
    public static void assertSize(Object container, int min, int max) {
        assertSize(container, min, max, null);
    }
    //assert the types of param list of the method
    public static void assertMethodParamTypes(Method method, String message, Class<?>... expectedTypes) {
        Class<?>[] actualTypes = method.getParameterTypes();
        boolean matches = true;
        if (actualTypes.length != expectedTypes.length) {
            matches = false;
        }
        for (int i = 0; i < expectedTypes.length && matches; i++) {
            if (actualTypes[i] != expectedTypes[i]) {
                matches = false;
            }
        }
        if (!matches) {
            if (message != null) {
                throw new DataTypeException(message);
            } else {
                throw new DataTypeException("actual method \"" + method + "\" doesn't match the expected.");
            }
        }
    }
    public static void assertMethodParamTypes(Method method, Class<?>... expectedTypes) {
        assertMethodParamTypes(method, null, expectedTypes);
    }
    //assert that all strings match the regex
    public static void assertRegex(String regex, String... strings) {
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == null || !pattern.matcher(strings[i]).matches()) {
                throw new DataContentException("strings[" + i + "]=" + (strings[i] == null ? "null" : ("\"" + strings[i] + "\"")) + " doesn't match regex:" + regex);
            }
        }
    }
}
