package com.zz.lib.common;

import java.util.Arrays;

public class ArrayUtil {
    /**
     * 判断数组是否为空（即为 null 或长度为 0）
     */
    public static <T> boolean isEmpty(T[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 合并多个数组为一个数组
     */
    public static <T> T[] concat(T[]... arrays) {
        int totalLength = 0;
        for (T[] arr : arrays) {
            totalLength += arr.length;
        }
        T[] result = Arrays.copyOf(arrays[0], totalLength);
        int offset = arrays[0].length;
        for (int i = 1; i < arrays.length; i++) {
            System.arraycopy(arrays[i], 0, result, offset, arrays[i].length);
            offset += arrays[i].length;
        }
        return result;
    }

    /** 判断数组中是否包含空值
     */
    public static <T> boolean containsNull(T[] array) {
        CheckUtil.mustNotNull(array);
        for (T obj : array) {
            if (obj == null) {
                return true;
            }
        }
        return false;
    }

    /**判断数组中是否全是空值
     */
    public static <T> boolean isAllNull(T[] array) {
        CheckUtil.mustNotNull(array);
        for (T obj : array) {
            if (obj != null) {
                return false;
            }
        }
        return true;
    }
}
