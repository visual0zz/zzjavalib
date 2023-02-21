package com.zz.lib.common;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;

public final class StringUtil {

    /**
     * 是否是一个空或者空白字符串，不是字符串或者字符串不为空白都会返回false，输入空白字符串或者null返回true
     *
     * @param obj 输入
     * @return 输入是否是空白字符串
     */
    public static boolean isBlankStr(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof CharSequence) {
            for (int i = 0; i < ((CharSequence) obj).length(); i++) {
                if (!CharUtil.isBlankChar(((CharSequence) obj).charAt(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 将对象转为字符串,会自动打断循环依赖并以 '<...>' 来进行标记
     *
     * @param object 对象
     * @return 字符串
     */
    public static String toString(Object object) {
        return toString(object, new HashSet<>());
    }

    private static String toString(Object object, HashSet<Object> dejaVu) {
        if (null == object) {
            return "null";
        }
        if (object instanceof long[]) {
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
            if (dejaVu.contains(object)) {
                return "<...>";
            }
            dejaVu.add(object);
            Object[] objects = (Object[]) object;
            StringBuilder builder = new StringBuilder("[");
            for (int i = 0; i < objects.length; i++) {
                Object subObj = objects[i];
                builder.append(toString(subObj, dejaVu));
                if (i < objects.length - 1) {
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

    /**
     * 用格式字符串拼接字符串
     *
     * @param format      格式字符串类似"xxx={},xxx={}" 这种，使用指定占位符表示填充数据的位置
     * @param placeHolder 占位符，例如{}
     * @param params      用于填充格式字符串空位的数据
     * @return 拼接后的字符串
     * 假如占位符是{} 那么使用\{}表示打印占位符本身，\\表示打印转义字符本身
     */
    public static String formatWith(CharSequence format, CharSequence placeHolder, Object... params) {
        String format_=format.toString();
        String placeHolder_=placeHolder.toString();
        if (isBlankStr(format_) || isBlankStr(placeHolder_)) {
            return format_;
        }
        final int strPatternLength = format_.length();
        final int placeHolderLength = placeHolder_.length();

        // 初始化定义好的长度以获得更好的性能
        final StringBuilder buffer = new StringBuilder(strPatternLength + 50);

        int current = 0;// 记录已经处理到的位置
        int placeHolderIndex;// 占位符所在位置
        for (int i = 0; i < params.length; i++) {
            placeHolderIndex = format_.indexOf(placeHolder_, current);
            if (placeHolderIndex == -1) {// 剩余部分无占位符
                break;
            }
            // 转义符
            if (placeHolderIndex > 0 && format_.charAt(placeHolderIndex - 1) == '\\') {// 转义符
                if (placeHolderIndex > 1 && format_.charAt(placeHolderIndex - 2) == '\\') {// 双转义符
                    // 转义符之前还有一个转义符，占位符依旧有效
                    buffer.append(format_, current, placeHolderIndex - 1);
                    buffer.append(params[i]==null?"":params[i].toString());
                    current = placeHolderIndex + placeHolderLength;
                } else {
                    // 占位符被转义
                    i--;
                    buffer.append(format_, current, placeHolderIndex - 1);
                    buffer.append(placeHolder_.charAt(0));
                    current = placeHolderIndex + 1;
                }
            } else {// 正常占位符
                buffer.append(format_, current, placeHolderIndex);
                buffer.append(params[i]==null?"":params[i].toString());
                current = placeHolderIndex + placeHolderLength;
            }
        }
        buffer.append(format_, current, strPatternLength);
        return buffer.toString();
    }

    /**
     * 用格式字符串拼接字符串
     *
     * @param format      格式字符串类似"xxx={},xxx={}" 这种，使用大括号表示填充数据的位置
     * @param params      用于填充格式字符串空位的数据
     * @return 拼接后的字符串
     * 使用\{}表示打印占位符本身，\\表示打印转义字符本身
     */
    public static String format(CharSequence format, Object... params) {
        return formatWith(format, "{}", params);
    }

    /**
     * 用格式字符串拼接字符串
     *
     * @param format      格式字符串类似"xxx={var1},xxx={var2}" 这种，使用大括号包变量名表示填充数据的位置
     * @param params      用于填充格式字符串空位的数据，其中key会被作为变量名，value会填充到对应位置
     * @return 拼接后的字符串
     */
    public static String format(CharSequence format, Map<?, ?> params) {
        if (null == format) {
            return null;
        }
        if (null == params || params.isEmpty()) {
            return format.toString();
        }

        String template = format.toString();
        String value;
        for (Map.Entry<?, ?> entry : params.entrySet()) {
            value = entry.getValue()==null?"":entry.getValue().toString();
            if (null == value) {
                continue;
            }
            template = template.replace("{" + entry.getKey() + "}", value);
        }
        return template;
    }
}
