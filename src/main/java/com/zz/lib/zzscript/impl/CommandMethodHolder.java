package com.zz.lib.zzscript.impl;

import com.zz.lib.common.CheckUtil;
import com.zz.lib.zzscript.Command;
import com.zz.lib.zzscript.Opt;
import com.zz.lib.zzscript.Req;
import com.zz.lib.zzscript.exception.CommandDefinationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * 指令的形参列表
 */
public class CommandMethodHolder {
    private HashMap<String,Integer> requiredParamTypes;
    private HashSet<String> optionalParamTypes;
    private Method method;

    /**
     * 代理一个Command对象的某个@Run修饰的函数
     * @param method 对应的函数
     */
    CommandMethodHolder(Method method){
        this.method=method;
        requiredParamTypes=new HashMap<>();
        optionalParamTypes=new HashSet<>();
        int index=0;
        for(Parameter parameter:method.getParameters()){
            Annotation[] annotations=parameter.getAnnotations();
            boolean opt=containsAnnotation(annotations, Opt.class);
            boolean req=containsAnnotation(annotations, Req.class);
            if(opt&&req){
                throw new CommandDefinationException("指令类定义错误,参数不能同时具有Opt 和 Req标记，method:"+method.toGenericString());
            }
            if(!opt&&!req){
                throw new CommandDefinationException("指令类定义错误,参数必须具有Opt 或 Req标记，method:"+method.toGenericString());
            }
            if(opt){
                optionalParamTypes.add(parameter.getName());
            }
            if(req){
                requiredParamTypes.put(parameter.getName(),index++);
            }
        }
    }

    /**
     * 使用标准格式参数调用这个函数
     * @param command 指令对象
     * @param argument 标准格式的参数
     * @param ignoreRedundantOptionalParam 是否忽略提供的参数中的多余的可选参数
     * @return 以标准格式返回的结果
     */
    public Argument invoke(Command command, Argument argument, boolean ignoreRedundantOptionalParam){
        //校验参数格式
        CheckUtil.mustSameSize(requiredParamTypes
                ,argument.requiredData
                ,"必须参数的数量不匹配，需要"+requiredParamTypes.size()+"个参数，实际获得"+argument.requiredData.size()+"个参数");
        if(!ignoreRedundantOptionalParam && !optionalParamTypes.containsAll(argument.optionalData.keySet())){
            throw new CommandDefinationException("输入了未定义参数，需要的参数列表为:"+optionalParamTypes+",提供的为:"+argument.optionalData);
        }

        //组装参数列表
        List<Object> paramObjects=new ArrayList<>();
        for(Parameter parameter:method.getParameters()){
            Object object;
            if(requiredParamTypes.containsKey(parameter.getName())){
                object=argument.requiredData.get(requiredParamTypes.get(parameter.getName()));
            }else if(optionalParamTypes.contains(parameter.getName())){
                object=argument.optionalData.get(parameter.getName());
            }else{
                throw new RuntimeException("error");
            }
            if(object!=null&&!parameter.getType().isAssignableFrom(object.getClass())){
                throw new CommandDefinationException("参数格式不匹配，需要:"+parameter.getType().getName()+",提供了:"+object.getClass());
            }
            paramObjects.add(object);
        }

        //调用方法
        Object ret;
        try {
            ret=method.invoke(command,paramObjects.toArray());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        //处理返回值
        if(ret!=null && Argument.class.isAssignableFrom(ret.getClass())){
            return (Argument) ret;
        }
        return Argument.of(new HashMap<>(),ret);
    }

    /**
     * 指令和某个参数列表的匹配度，是为了在同一个指令的多个实现中寻找应该调用哪个。
     * @param argument 实际参数
     * @return 匹配度 0到1之间
     */
    public double compatibility(Argument argument){
        return 0;//todo 计算匹配度
    }
    private boolean containsAnnotation(Annotation[]annotations,Class<? extends Annotation> annoType){
        for(Annotation anno:annotations){
            if(anno.getClass().equals(annoType)){
                return true;
            }
        }
        return false;
    }
}
