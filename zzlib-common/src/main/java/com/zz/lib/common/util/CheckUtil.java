package com.zz.lib.common.util;

import com.zz.lib.common.exception.DataContentException;
import com.zz.lib.common.exception.DataSizeException;
import com.zz.lib.common.exception.DataTypeException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class CheckUtil {
    private final static String[] sizeMethodNames = {"size", "length"};
    private final static String[] sizeFieldNames = {"size", "length"};

    private CheckUtil() {
    }

    public static void mustTrue(Boolean... conditions) {
        mustTrue(null, conditions);
    }

    public static void mustTrue(String message, Boolean... conditions) {
        if (!Arrays.stream(conditions).allMatch(e -> e)) {
            if (message == null) {
                message = "some conditions are false,conditions=" + Arrays.toString(conditions);
            }
            throw new DataContentException(message);
        }
    }

    public static <T> void mustEquals(T actual, T expected, String message) {
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

    public static <T> void mustEquals(T actual, T expected) {
        mustEquals(actual, expected, null);
    }

    public static void mustNotNull(Object object, Object... objects) {
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
    public static <T> void mustIsA(Object object, Class<T> clazz, String message) {
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

    public static <T> void mustIsA(Object object, Class<T> clazz) {
        mustIsA(object, clazz, null);
    }

    /**
     * 声明一个容器具有特定的尺寸
     *
     * @param container 容器
     * @param min       最小尺寸（包含）
     * @param max       最大尺寸（包含）
     * @param message   如果不符合，那么抛出异常携带的信息
     */
    public static void mustMatchSize(Object container, int min, int max, String message) {
        int actualSizeValue = getContainerSize(container);
        if (actualSizeValue < min || actualSizeValue > max) {
            if (message != null) {
                throw new DataSizeException(message);
            } else {
                throw new DataSizeException("container size=" + actualSizeValue + ",not in [" + min + "," + max + "].");
            }
        }
    }

    /**
     * 声明两个容器尺寸相等
     *
     * @param container1 容器1
     * @param container2 容器2
     * @param message    如果不符合，那么抛出异常携带的信息
     */
    public static void mustSameSize(Object container1, Object container2, String message) {
        if (getContainerSize(container1) != getContainerSize(container2)) {
            if (message != null) {
                throw new DataSizeException(message);
            } else {
                throw new DataSizeException("container size not equal.");
            }
        }
    }

    public static void mustSameSize(Object container1, Object container2) {
        mustSameSize(container1, container2, null);
    }

    private static int getContainerSize(Object container) {
        Class<?> clazz = container.getClass();
        Integer actualSizeValue = null;
        if (clazz.isArray()) {
            actualSizeValue = Array.getLength(container);
        } else {
            Field sizeField = null;
            for (String name : sizeFieldNames) {
                try {
                    if (sizeField == null) {
                        sizeField = clazz.getField(name);
                    }
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
                    if (sizeMethod == null) {
                        sizeMethod = clazz.getMethod(name);
                    }
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
        }
        return actualSizeValue;
    }

    public static void mustMatchSize(Object container, int min, int max) {
        mustMatchSize(container, min, max, null);
    }

    //assert the types of param list of the method
    public static void mustWithParamTypes(Method method, String message, Class<?>... expectedTypes) {
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

    public static void mustWithParamTypes(Method method, Class<?>... expectedTypes) {
        mustWithParamTypes(method, null, expectedTypes);
    }

    //assert that all strings match the regex
    public static void mustMatchRegex(String regex, String... strings) {
        Pattern pattern = Pattern.compile(regex);
        for (int i = 0; i < strings.length; i++) {
            if (strings[i] == null || !pattern.matcher(strings[i]).matches()) {
                throw new DataContentException("strings[" + i + "]=" + (strings[i] == null ? "null" : ("\"" + strings[i] + "\"")) + " doesn't match regex:" + regex);
            }
        }
    }
}
