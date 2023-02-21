package com.zz.lib.common.json;

import com.zz.lib.common.StringUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonStrParser {

    public int index;
    public final CharSequence charSequence;
    boolean ended;
    public JsonStrParser(CharSequence charSequence){
        this.charSequence =charSequence;
        index=0;
        ended=false;
    }
    public char next() {
        if (ended) {
            throw new JsonParseException("unexpected eof.");
        }
        if (index >= charSequence.length() - 1) {
            ended = true;
        }
        return charSequence.charAt(index++);
    }
    public char next(char c){
        char n=next();
        if(n!=c){
            throw new JsonParseException("expected "+c+" but got "+n+" [pos:"+--index+"]");
        }
        return n;
    }
    public String next(int n) {
        if (n == 0) {
            return "";
        }
        char[] result = new char[n];
        for (int i = 0; i < n; i++) {
            result[i] = next();
        }
        return new String(result);
    }
    public char nextClean(){
        char c;
        while (true) {
            c = next();
            if (c == 0 || c > ' ') {
                return c;
            }
        }
    }
    public String nextString(char quote) {
        char c;
        StringBuilder sb = new StringBuilder();
        while (true) {
            c = next();
            switch (c) {
                case 0:
                case '\n':
                case '\r':
                    throw new RuntimeException("Unterminated string");
                case '\\':// 转义符
                    c = next();
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
                            sb.append((char) Integer.parseInt(next(4), 16));
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
    public Map<String,Object> nextObject(){
        HashMap<String,Object> result=new HashMap();
        while(true){
            char c=nextClean();
            switch (c){
                case '"':
                case '\'':
                    String key=nextString(c);
                    next(':');
                    Object value=nextValue();
                    if(result.containsKey(key)){
                        throw new JsonParseException("duplicate key found:"+key);
                    }
                    result.put(key,value);
                    char n=nextClean();
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
    public List<Object> nextArray(){
        List<Object> result=new ArrayList<>();
        while(true){
            Object value=nextValue();
            result.add(value);
            char c=nextClean();
            if(c==']'){
                return result;
            }
            if(c!=','){
                throw new JsonParseException("unexpected char found: "+c+" [pos:"+--index+"]");
            }
        }
    }
    public Object nextValue() {
        char c = this.nextClean();
        switch (c) {
            case '"':
            case '\'':
                return this.nextString(c);
            case '{':
                return nextObject();
            case '[':
                return nextArray();
        }
        StringBuilder sb = new StringBuilder();
        while (c >= ' ' && ",:]}/\\\"[{;=#".indexOf(c) < 0) {
            sb.append(c);
            if(ended){
                break;
            }
            c = this.next();
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
