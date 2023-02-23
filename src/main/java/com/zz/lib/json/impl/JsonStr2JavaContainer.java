package com.zz.lib.json.impl;

import com.zz.lib.common.CharUtil;
import com.zz.lib.common.StringUtil;
import com.zz.lib.json.JsonParseException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonStr2JavaContainer {
    public static Object parse(CharSequence json){
        JsonStr2JavaContainer parser=new JsonStr2JavaContainer(json);
        Object result=parser.nextValue();
        while(!parser.ended){
            if(!CharUtil.isBlankChar(parser.nextChar())){
                throw new JsonParseException("json string with redundant tail:"+json.subSequence(parser.index,json.length()));
            }
        }
        return result;
    }

    int index;
    final CharSequence charSequence;
    boolean ended;
    JsonStr2JavaContainer(CharSequence charSequence){
        this.charSequence =charSequence;
        index=0;
        ended=false;
    }
    char nextChar() {
        if (ended) {
            throw new JsonParseException("unexpected eof.");
        }
        if (index >= charSequence.length() - 1) {
            ended = true;
        }
        return charSequence.charAt(index++);
    }
    char nextChar(char c){
        char n= nextChar();
        if(n!=c){
            throw new JsonParseException("expected "+c+" but got "+n+" [pos:"+--index+"]");
        }
        return n;
    }
    String nextChar(int n) {
        if (n == 0) {
            return "";
        }
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            result[i] = nextChar();
        }
        return new String(result);
    }
    char nextCleanChar(){
        char c;
        while (true) {
            c = nextChar();
            if (c == 0 || c > ' ') {
                return c;
            }
        }
    }
    String parseString(char quote) {
        char c;
        StringBuilder sb = new StringBuilder();
        while (true) {
            c = nextChar();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    throw new RuntimeException("Unterminated string");
                case '\\':// 转义符
                    c = nextChar();
                    switch (c) {
                        case 'b':
                            sb.append('\b');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'f':
                            sb.append('\f');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 'u':// Unicode符
                            sb.append((char) Integer.parseInt(nextChar(4), 16));
                            break;
                        case '"':
                        case '\'':
                        case '\\':
                        case '/':
                            sb.append(c);
                            break;
                        default:
                            throw new RuntimeException("Illegal escape.");
                    }
                    break;
                default:
                    if (c == quote) {
                        return sb.toString();
                    }
                    sb.append(c);
            }
        }
    }
    Map<String,Object> parseObject(){
        HashMap<String,Object> result=new HashMap();
        while(true){
            char c= nextCleanChar();
            switch (c){
                case '"':
                case '\'':
                    String key= parseString(c);
                    nextChar(':');
                    Object value=nextValue();
                    if(result.containsKey(key)){
                        throw new JsonParseException("duplicate key found:"+key);
                    }
                    if(value!=null){
                        result.put(key,value);
                    }
                    char n= nextCleanChar();
                    if(n==','){
                        continue;
                    }else if(n!='}'){
                        throw new JsonParseException("unexpected char found: "+n+" [pos:"+--index+"]");
                    }
                case '}':
                    return result;
                default:
                    throw new JsonParseException("unexpected char found: "+c+" [pos:"+--index+"]");
            }
        }
    }
    List<Object> parseArray(){
        List<Object> result=new ArrayList<>();
        while(true){
            Object value=nextValue();
            if(value!=null) {
                result.add(value);
            }
            char c= nextCleanChar();
            if(c==']'){
                return result;
            }
            if(c!=','){
                throw new JsonParseException("unexpected char found: "+c+" [pos:"+--index+"]");
            }
        }
    }
    Object nextValue() {
        char c = this.nextCleanChar();
        switch (c) {
            case '"':
            case '\'':
                return this.parseString(c);
            case '{':
                return parseObject();
            case '[':
                return parseArray();
        }
        StringBuilder sb = new StringBuilder();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            if(ended){
                break;
            }
            c = this.nextChar();
        }
        String string = sb.toString().trim();
        if(!ended) {
            index--;
        }
        if (StringUtil.isBlankStr(string) || "null".equalsIgnoreCase(string)) {
            return null;
        }
        if ("true".equalsIgnoreCase(string)) {
            return Boolean.TRUE;
        }
        if ("false".equalsIgnoreCase(string)) {
            return Boolean.FALSE;
        }
        char b = string.charAt(0);
        if ((b >= '0' && b <= '9') || b == '-') {
            if (string.contains(".")|| string.contains("e")|| string.contains("E")) {
                return new BigDecimal(string);
            } else {
                long longValue=Long.parseLong(string);
                if(string.equals(Long.toString(longValue)) && (int)longValue==longValue){
                    return (int)longValue;
                }
                return longValue;
            }
        }
        throw new JsonParseException("unparseable string: "+ string);
    }

}
