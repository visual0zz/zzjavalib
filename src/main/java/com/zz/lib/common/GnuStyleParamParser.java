package com.zz.lib.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
/**
 * 用于解析main函数的入参的工具类
 */
public class GnuStyleParamParser {
    private GnuStyleParamParser(){}
    HashMap<String,Class<?>> types=new HashMap<>();
    boolean ignoreRedundantOptionalParam=false;
    public static RequireParamNotSet builder(){
        return new Builder();
    }
    public static class Builder implements RequireParamNotSet,RequireParamSet{
        HashMap<String,Class<?>> types=new HashMap<>();
        boolean ignoreRedundantOptionalParam=false;
        @Override
        public GnuStyleParamParser build(){
            GnuStyleParamParser paramParser=new GnuStyleParamParser();
            paramParser.types=this.types;
            paramParser.ignoreRedundantOptionalParam=this.ignoreRedundantOptionalParam;
            return paramParser;
        }
        @Override
        public Builder optionalBoolean(String paramName){
            types.put(paramName,Boolean.class);
            return this;
        }
        @Override
        public Builder optionalString(String paramName){
            types.put(paramName,String.class);
            return this;
        }
        @Override
        public<T extends Enum<T>> Builder optionalEnum(String paramName,Class<T> type){
            types.put(paramName,type);
            return this;
        }
        @Override
        public Builder optionalNumber(String paramName){
            types.put(paramName, BigInteger.class);
            return this;
        }
        @Override
        public Builder optionalDecimal(String paramName){
            types.put(paramName, BigDecimal.class);
            return this;
        }

        /**
         * 设置必须参数的数目
         * @param min 最少min个参数
         * @param max 最多max个参数
         * @return this
         */
        public Builder requiredParam(int min,int max){
            return this;
        }
        public Builder requiredParam(int params){
            return requiredParam(params,params);
        }
        public Builder ignoreRedundantOptionalParam(boolean ignore){
            ignoreRedundantOptionalParam=ignore;
            return this;
        }
        public Builder ignoreRedundantOptionalParam(){
            return ignoreRedundantOptionalParam(true);
        }
    }
    public interface RequireParamSet{
        RequireParamSet optionalBoolean(String paramName);
        RequireParamSet optionalString(String paramName);
        <T extends Enum<T>>RequireParamSet optionalEnum(String paramName,Class<T> type);
        RequireParamSet optionalNumber(String paramName);
        RequireParamSet optionalDecimal(String paramName);
        RequireParamSet ignoreRedundantOptionalParam(boolean ignore);
        RequireParamSet ignoreRedundantOptionalParam();
        GnuStyleParamParser build();
    }
    public interface RequireParamNotSet{
        RequireParamSet requiredParam(int min,int max);
        RequireParamSet requiredParam(int params);
        RequireParamNotSet optionalBoolean(String paramName);
        RequireParamNotSet optionalString(String paramName);
        <T extends Enum<T>>RequireParamNotSet optionalEnum(String paramName,Class<T> type);
        RequireParamNotSet optionalNumber(String paramName);
        RequireParamNotSet optionalDecimal(String paramName);
        RequireParamNotSet ignoreRedundantOptionalParam(boolean ignore);
        RequireParamNotSet ignoreRedundantOptionalParam();
    }
}
