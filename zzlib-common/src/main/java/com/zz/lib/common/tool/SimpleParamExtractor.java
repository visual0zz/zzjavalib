package com.zz.lib.common.tool;
import com.zz.lib.common.util.StringUtil;

import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 用于解析main函数的入参的工具，仿Gnu风格的参数格式。
 */
public class SimpleParamExtractor {
    private static final ParamParser BOOLEAN_GHOST_PARSER= (k,v) -> {
        throw new Error("this object is just a placeholder,should not be invoked.");
    };
    int requiredParamMin;
    int requiredParamMax;
    private static final int REQUIRED_PARAMS_ORDER_FORMAT=3;
    private static final String REQUIRED_PARAM_PREFIX="__require_var_";
    private ConcurrentHashMap<String, ParamParser> parsers;
    private SimpleParamExtractor(){}
    boolean ignoreRedundantOptionalParam=false;
    public ParamInfo extract(String...args)throws IllegalArgumentException{
        HashMap<String,Object> result=new HashMap<>();
        //把所有的布尔型参数预先塞上false
        parsers.entrySet().stream()
                .filter(e->e.getValue()==BOOLEAN_GHOST_PARSER)
                .forEach(e->result.put(e.getKey(),false));
        int requiredParamIndex=0;
        for(int i=0;i<args.length;i++){
            String token=args[i];
            if(token.startsWith("--")){
                String mainPart=token.substring(2);
                String paramName=mainPart.contains("=")?mainPart.substring(0,mainPart.indexOf("=")):mainPart;
                ParamParser parser;
                if(parsers.get(mainPart)==BOOLEAN_GHOST_PARSER){
                    //布尔型参数没有参数值，直接返回
                    result.put(mainPart,true);
                }else if((parser=parsers.get(paramName))==null && !ignoreRedundantOptionalParam){
                    //找不到参数的解析器且配置不忽略多余参数，则报错
                    throw new IllegalArgumentException("unknown param :"+ StringUtil.escapeString(paramName));
                }else{
                    //根据token中是否包含等号来判断参数值是在等号后面还是下一个token
                    String paramValue=mainPart.contains("=")
                            ?mainPart.substring(mainPart.indexOf("=")+1)
                            :(++i<args.length?args[i]:null);
                    if(paramValue==null){
                        throw new IllegalArgumentException("param "+StringUtil.escapeString(paramName)+" without value.");
                    }
                    result.put(paramName,parser==null?paramValue:parser.parse(paramName,paramValue));
                }
            }else if(token.startsWith("-")){
                if(token.length()<=2){
                    //形如 -m
                    String paramName=token.substring(1);
                    ParamParser parser;
                    if(parsers.get(paramName)==BOOLEAN_GHOST_PARSER){
                        //布尔型参数没有参数值，直接返回
                        result.put(paramName,true);
                    }else if((parser=parsers.get(paramName))==null && !ignoreRedundantOptionalParam){
                        //找不到参数的解析器且配置不忽略多余参数，则报错
                        throw new IllegalArgumentException("unknown param :"+StringUtil.escapeString(paramName));
                    }else{
                        //根据token中是否包含等号来判断参数值是在等号后面还是下一个token
                        String paramValue=++i<args.length?args[i]:null;
                        if(paramValue==null){
                            throw new IllegalArgumentException("param "+StringUtil.escapeString(paramName)+" without value.");
                        }
                        result.put(paramName,parser==null?paramValue:parser.parse(paramName,paramValue));
                    }
                }else if(token.charAt(2)=='='){
                    //形如 -m=xxx
                    String paramName=token.substring(1,2);
                    String paramValue=token.substring(3);
                    ParamParser parser=parsers.get(paramName);
                    if(parser==null && !ignoreRedundantOptionalParam){
                        //找不到参数的解析器且配置不忽略多余参数，则报错
                        throw new IllegalArgumentException("unknown param :"+StringUtil.escapeString(paramName));
                    }
                    result.put(paramName,parser==null?paramValue:parser.parse(paramName,paramValue));
                }else if(token.contains("=")) {
                    //形如 -ma=sss 属于格式错误，无法解析
                    throw new IllegalArgumentException("unknown: "+token);
                }else{
                    //形如 -mav 属于多个单字母布尔参数的堆叠
                    for(char c:token.substring(1).toCharArray()){
                        ParamParser parser=parsers.get(String.valueOf(c));
                        if(parser!=null && parser!=BOOLEAN_GHOST_PARSER){
                            throw new IllegalArgumentException("wrong usage of param :"+StringUtil.escapeString(String.valueOf(c)));
                        }else if(parser==null && !ignoreRedundantOptionalParam){
                            throw new IllegalArgumentException("unknown param :"+StringUtil.escapeString(String.valueOf(c)));
                        }else{
                            result.put(String.valueOf(c),true);
                        }
                    }
                }
            }else{
                if(requiredParamIndex>requiredParamMax){
                    throw new IllegalArgumentException("too many param");
                }
                NumberFormat format =NumberFormat.getIntegerInstance();
                format.setMinimumIntegerDigits(REQUIRED_PARAMS_ORDER_FORMAT);
                result.put(REQUIRED_PARAM_PREFIX+ format.format(requiredParamIndex++),token);
            }
        }
        if(requiredParamIndex<requiredParamMin){
            throw new IllegalArgumentException("not enough param");
        }
        return new ParamInfo(result);
    }
    public static RequireParamNotSet builder(){
        return new Builder();
    }

    @Override
    public String toString() {
        return "SimpleParamExtractor{" +
                "requiredParamMin=" + requiredParamMin +
                ", requiredParamMax=" + requiredParamMax +
                ", parsers=" + parsers.keySet() +
                ", ignoreRedundantOptionalParam=" + ignoreRedundantOptionalParam +
                '}';
    }

    private static class Builder implements RequireParamNotSet,RequireParamSet{
        int requiredParamMin=0;
        int requiredParamMax=Integer.MAX_VALUE;
        ConcurrentHashMap<String,ParamParser> parsers=new ConcurrentHashMap<>();
        boolean ignoreRedundantOptionalParam=false;
        @Override
        public SimpleParamExtractor build(){
            SimpleParamExtractor extractor =new SimpleParamExtractor();
            extractor.parsers=this.parsers;
            extractor.requiredParamMin=this.requiredParamMin;
            extractor.requiredParamMax=this.requiredParamMax;
            extractor.ignoreRedundantOptionalParam=this.ignoreRedundantOptionalParam;
            return extractor;
        }

        /**
         * 定义一个布尔型参数，例如git merge --abort 中的abort，只有存在和不存在两个状态，没有参数内容。
         * 布尔型参数如果参数名只有一个字母，则支持堆叠，例如 ps -ef 就是两个参数e和f的堆叠
         * @param paramName 参数名，例如git merge --abort 中的abort
         * @return this
         */
        @Override
        public Builder optionalBoolean(String paramName){
            optionalParam(paramName,BOOLEAN_GHOST_PARSER);
            return this;
        }
        /**
         * 定义一个字符串参数，例如git commit -m aaa 中的m，包含字符串格式的参数内容aaa。
         * @param paramName 参数名，例如git commit -m aaa 中的m
         * @return this
         */
        @Override
        public Builder optionalString(String paramName){
            return optionalParam(paramName,(k,v)->v);
        }
        /**
         * 定义一个枚举参数，例如git log --pretty=oneline 中的pretty，包含字符串格式的参数内容oneline。
         * 和字符串参数的区别就是参数值只有少数几个选项，如果输入不在选项中则直接报错
         *
         * @param paramName  参数名，例如git log --pretty=oneline 中的pretty
         * @param ignoreCase 忽略大小写
         * @return this
         */
        @Override
        public<T extends Enum<T>> Builder optionalEnum(String paramName, Class<T> type, boolean ignoreCase){
            return optionalParam(paramName,(k,v)->{
                Optional<T> value=Arrays.stream(type.getEnumConstants()).filter(a->{
                    if(ignoreCase){
                        return a.name().toLowerCase(Locale.ROOT).equals(v.toLowerCase(Locale.ROOT));
                    }else{
                        return a.name().equals(v);
                    }
                }).findAny();
                if(!value.isPresent()){
                    throw new IllegalArgumentException("unknown value for param "+StringUtil.escapeString(paramName)+" :"+StringUtil.escapeString(v));
                }
                return value.get();
            });
        }
        /**
         * 定义一个参数
         * @param paramName 参数名称
         * @param parser 参数解析器，字符串会被投入这个解析器来解析出对应的参数值
         * @return this
         */
        @Override
        public Builder optionalParam(String paramName,ParamParser parser){
            parsers.put(paramName,parser);
            return this;
        }

        /**
         * 设置必须参数的数目,必须参数指的是指令中按次序给出的无名参数，例如 echo
         * @param min 最少min个必须参数
         * @param max 最多max个必须参数
         * @return this
         */
        public Builder requiredParam(int min,int max){
            this.requiredParamMin=min;
            this.requiredParamMax=max;
            return this;
        }
        /**
         * 设置必须参数的数目
         * @param params 必须参数个数
         * @return this
         */
        public Builder requiredParam(int params){
            return requiredParam(params,params);
        }

        /**
         * 忽略多余的可选参数
         * @param ignore true-忽略 false-不忽略
         * @return this
         */
        public Builder allowExtraOptionalParam(boolean ignore){
            ignoreRedundantOptionalParam=ignore;
            return this;
        }
        /**
         * 忽略多余的可选参数
         * @return this
         */
        public Builder allowExtraOptionalParam(){
            return allowExtraOptionalParam(true);
        }
    }
    public interface RequireParamSet{
        RequireParamSet optionalBoolean(String paramName);
        RequireParamSet optionalString(String paramName);
        <T extends Enum<T>>RequireParamSet optionalEnum(String paramName,Class<T> type, boolean ignoreCase);
        RequireParamSet optionalParam(String paramName,ParamParser parser);
        RequireParamSet allowExtraOptionalParam(boolean ignore);
        RequireParamSet allowExtraOptionalParam();
        SimpleParamExtractor build();
    }
    public interface RequireParamNotSet{
        RequireParamSet requiredParam(int min,int max);
        RequireParamSet requiredParam(int params);
        RequireParamNotSet optionalBoolean(String paramName);
        RequireParamNotSet optionalString(String paramName);
        <T extends Enum<T>>RequireParamNotSet optionalEnum(String paramName, Class<T> type, boolean ignoreCase);
        RequireParamNotSet optionalParam(String paramName,ParamParser parser);
        RequireParamNotSet allowExtraOptionalParam(boolean ignore);
        RequireParamNotSet allowExtraOptionalParam();
    }
    public interface ParamParser{
        Object parse(String paramName,String paramValue);
    }
    public static class ParamInfo{
        Map<String,Object> optional;
        List<String> required;
        public ParamInfo(Map<String,Object> paramInfo){
            optional=new HashMap<>();
            List<Map.Entry<String,Object>> required=new ArrayList<>();
            for(Map.Entry<String,Object> entry:paramInfo.entrySet()){
                if(entry.getKey().startsWith(REQUIRED_PARAM_PREFIX)){
                    required.add(entry);
                }else{
                    optional.put(entry.getKey(),entry.getValue());
                }
            }
            this.required=required.stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .map(e->(String)e)
                    .collect(Collectors.toList());
        }
        public Object getOpt(String paramName){
            return optional.get(paramName);
        }
        public <T> T getOpt(String paramName,Class<T>paramType){
            Object obj=getOpt(paramName);
            if(paramType.isAssignableFrom(obj.getClass())){
                return (T)obj;
            }
            throw new IllegalArgumentException("input param type error: "+StringUtil.escapeString(paramName));
        }
        public List<String> getReqs(){
            return required;
        }

        @Override
        public String toString() {
            return "ParamInfo{" +
                    "optional=" + optional +
                    ", required=" + required +
                    '}';
        }
    }
}
