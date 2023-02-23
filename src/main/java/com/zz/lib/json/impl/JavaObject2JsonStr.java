package com.zz.lib.json.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JavaObject2JsonStr {
    private static JavaObject2JsonStr BUILDER = new JavaObject2JsonStr(false);
    private static JavaObject2JsonStr FORMATTED_BUILDER = new JavaObject2JsonStr(true);

    JavaObject2JsonStr(boolean formatted) {
        this.formatted = formatted;
    }

    public static String toJson(Object object) {
        return BUILDER.construct(object, 1);
    }

    public static String toFormatJson(Object object) {
        return FORMATTED_BUILDER.construct(object, 1);
    }

    boolean formatted;

    String construct(Object object, int level) {
        if (object == null) {
            return "null";
        } else if (object instanceof Integer || object instanceof Long || object instanceof Short || object instanceof Byte) {
            return String.valueOf(object);
        } else if (object instanceof BigDecimal) {
            return constructDecimal((BigDecimal) object);
        } else if (object instanceof Float) {
            return constructDecimal(BigDecimal.valueOf((Float) object));
        } else if (object instanceof Double) {
            return constructDecimal(BigDecimal.valueOf((Double) object));
        } else if (object instanceof List) {
            return constructArray((List<Object>) object, level);
        } else if (object instanceof Map) {
            return constructMap((Map<String, Object>) object, level);
        } else if (object instanceof byte[]) {
            List<Object> array = new ArrayList<>();
            for (byte b : (byte[]) object) {
                array.add((int) b);
            }
            return constructArray(array, level);
        } else if (object instanceof short[]) {
            List<Object> array = new ArrayList<>();
            for (short b : (short[]) object) {
                array.add((int) b);
            }
            return constructArray(array, level);
        } else if (object instanceof int[]) {
            List<Object> array = new ArrayList<>();
            for (int b : (int[]) object) {
                array.add(b);
            }
            return constructArray(array, level);
        } else if (object instanceof long[]) {
            List<Object> array = new ArrayList<>();
            for (long b : (long[]) object) {
                array.add(b);
            }
            return constructArray(array, level);
        } else if (object instanceof float[]) {
            List<Object> array = new ArrayList<>();
            for (float b : (float[]) object) {
                array.add(BigDecimal.valueOf(b));
            }
            return constructArray(array, level);
        } else if (object instanceof double[]) {
            List<Object> array = new ArrayList<>();
            for (double b : (double[]) object) {
                array.add(BigDecimal.valueOf(b));
            }
            return constructArray(array, level);
        } else if (object instanceof char[]) {
            return constructString(String.valueOf((char[]) object));
        } else if (object instanceof Character[]) {
            StringBuilder builder = new StringBuilder();
            for (Character c : (Character[]) object) {
                builder.append(c);
            }
            return constructString(builder.toString());
        } else if (object instanceof String || object instanceof Character) {
            return constructString("" + object);
        }else if(object instanceof Boolean){
            return object.toString();
        }
        //todo 判断对象是直接继承Object的简单对象，直接提取内部属性
        return "error...";
    }

    String constructArray(List<Object> array, int level) {
        StringBuilder result = new StringBuilder();
        result.append("[");
        if (formatted) {
            result.append("\n");
        }
        boolean emptyArray=true;
        for (int i = 0; i < array.size(); i++) {
            Object obj = array.get(i);
            if (obj != null) {
                emptyArray=false;
                leadingSpace(result, level);
                result.append(construct(obj, level + 1));
                if (i < array.size() - 1) {
                    result.append(',');
                }
                if (formatted) {
                    result.append("\n");
                }
            }
        }
        if(emptyArray){
            return "[]";
        }
        leadingSpace(result, level - 1);
        result.append(']');
        return result.toString();
    }

    String constructMap(Map<String, Object> map, int level) {
        StringBuilder result = new StringBuilder();
        result.append("{");
        if (formatted) {
            result.append("\n");
        }
        boolean emptyMap=true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (entry.getValue() != null) {
                emptyMap=false;
                leadingSpace(result, level);
                result.append('"').append(entry.getKey()).append("\": ").append(construct(entry.getValue(), level + 1)).append(',');
                if (formatted) {
                    result.append("\n");
                }
            }
        }
        if(emptyMap){
            return "{}";
        }
        if (result.charAt(result.length() - 1) == ',') {
            result.deleteCharAt(result.length() - 1);
        }
        leadingSpace(result,level-1);
        result.append("}");
        return result.toString();
    }

    String constructDecimal(BigDecimal number) {
        number = number.stripTrailingZeros();
        StringBuilder result = new StringBuilder();
        if (number.abs().compareTo(new BigDecimal("0.00001")) > 0 && number.abs().compareTo(new BigDecimal("1e5")) < 0 || number.compareTo(new BigDecimal("0")) == 0) {
            result.append(number.toPlainString());
        } else {
            result.append(number.toEngineeringString());
        }
        return result.toString();
    }

    String constructString(String str) {
        StringBuilder result = new StringBuilder();
        result.append('"');
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            switch (c) {
                case '\\':
                case '"':
                    result.append("\\");
                    result.append(c);
                    break;
                default:
                    String replace;
                    switch (c) {
                        case '\b':
                            replace = "\\b";
                            break;
                        case '\t':
                            replace = "\\t";
                            break;
                        case '\n':
                            replace = "\\n";
                            break;
                        case '\f':
                            replace = "\\f";
                            break;
                        case '\r':
                            replace = "\\r";
                            break;
                        default:
                            if (c < ' ' ||
                                    (c >= '\u0080' && c <= '\u00a0') ||
                                    (c >= '\u2000' && c <= '\u2010') ||
                                    (c >= '\u2028' && c <= '\u202F') ||
                                    (c >= '\u2066' && c <= '\u206F')
                            ) {
                                replace = "\\u" + Integer.toString(c, 16);
                                break;
                            } else {
                                replace = Character.toString(c);
                                break;
                            }
                    }
                    result.append(replace);
            }
        }
        result.append('"');
        return result.toString();
    }

    private void leadingSpace(StringBuilder builder, int level) {
        if (formatted) {
            for (int i = 0; i < level; i++) {
                builder.append("  ");
            }
        }
    }

}
