package com.zz.lib.common;

import com.zz.lib.common.exception.DataSizeException;
import java.util.Arrays;
import java.util.HashSet;

public final class StringUtil {
    /**
     * 用格式字符串拼接字符串
     *
     * @param format 格式字符串类似"xxx={},xxx={}" 这种，使用大括号表示填充数据的位置
     * @param params 用于填充格式字符串空位的数据
     * @return 拼接后的字符串
     */
    public static String format(String format, Object... params) {
        StringBuilder builder = new StringBuilder();
        int placeHolderCount = 0;
        for (int i = 0; i < format.length(); i++) {
            char currentChar = format.charAt(i);
            if (currentChar == '{' && i + 1 < format.length() && format.charAt(i + 1) == '}') {
                if (placeHolderCount < params.length) {
                    builder.append(params[placeHolderCount].toString());
                }
                placeHolderCount++;
                i++;
            } else {
                builder.append(currentChar);
            }
        }
        if (placeHolderCount != params.length) {
            throw new DataSizeException("need " + placeHolderCount + " param,but got " + params.length);
        }
        return builder.toString();
    }

    /**
     * 是否是一个空白字符串，不是字符串或者字符串不为空白都会返回false，输入空白字符串返回true
     *
     * @param obj 输入
     * @return 输入是否是空白字符串
     */
    public static boolean isBlankString(Object obj) {
        if (null == obj) {
            return true;
        } else if (obj instanceof CharSequence) {
            final int length;
            if ((length = ((CharSequence) obj).length()) == 0) {
                return true;
            }
            for (int i = 0; i < length; i++) {
                // 只要有一个非空字符即为非空字符串
                if (!CharUtil.isBlankChar(((CharSequence) obj).charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 将对象转为字符串,会自动打断循环依赖并以 [...] 来进行标记
     *
     * @param object 对象
     * @return 字符串
     */
    public static String toString(Object object){
        return toString(object,new HashSet<>());
    }
    private static String toString(Object object,HashSet<Object> dejaVu) {
        if (null == object) {
            return "null";
        } if (object instanceof long[]) {
            return Arrays.toString((long[]) object);
        } else if (object instanceof int[]) {
            return Arrays.toString((int[]) object);
        } else if (object instanceof short[]) {
            return Arrays.toString((short[]) object);
        } else if (object instanceof char[]) {
            return Arrays.toString((char[]) object);
        } else if (object instanceof byte[]) {
            return Arrays.toString((byte[]) object);
        } else if (object instanceof boolean[]) {
            return Arrays.toString((boolean[]) object);
        } else if (object instanceof float[]) {
            return Arrays.toString((float[]) object);
        } else if (object instanceof double[]) {
            return Arrays.toString((double[]) object);
        } else if (object.getClass().isArray()) {
            if(dejaVu.contains(object)){
                return "[...]";
            }
            dejaVu.add(object);
            Object[]objects=(Object[])object;
            StringBuilder builder=new StringBuilder("[");
            for(int i=0;i<objects.length;i++){
                Object subObj=objects[i];
                builder.append(toString(subObj,dejaVu));
                if(i<objects.length-1){
                    builder.append(", ");
                }
            }
            builder.append("]");
            dejaVu.remove(object);
            return builder.toString();
        }
        //todo 对于没有定义toString的对象，自动读取public字段来拼装string
        return object.toString();
    }

}
