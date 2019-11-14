package com.zz.utils.command;

import com.zz.utils.basicwork.ShellColor;
import com.zz.utils.command.impl.CommandManager;
import com.zz.utils.command.impl.CommandTriger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 本接口表示一个CLI界面指令，具体指令要实现本接口。
 */
public abstract class Command {

    public CommandManager manager;//储存自己所属的管理类的实例，由管理器对象从外部注入。
    public CommandTriger triger;
    /**
     * 指令的执行入口，由管理器自动调用
     * @return 是否成功执行
     */
    public abstract void run();

    /**
     * 用户用于自定义指令字符串的函数
     * @return 本类代表的指令字符串
     */
    public abstract String commandLine();

    /**
     * 用户自定义自我介绍函数
     * @param inShort 需要的是一个简短介绍还是一个详细介绍
     * @return 介绍字符串
     */
    public abstract String selfIntroduce(boolean inShort);



    /**
     * 用于生成自身帮助信息条目的函数
     * @return 本指令的帮助信息
     */
    public final String generateHelpInformationThroughReflection(boolean inshort){
        StringBuilder result=new StringBuilder();
        Class clazz=this.getClass();

        result.append(ShellColor.blue+ShellColor.hightlight+this.commandLine()+ShellColor.clear);//写上指令名

        boolean hasOptions=false;
        for(Field field:clazz.getDeclaredFields()){
            ArgumentMark argumentMark=field.getAnnotation(ArgumentMark.class);
            if(argumentMark!=null){//如果这个field确实被ArgumentMark修饰
                if(argumentMark.value().equals("")){
                    result.append(" ").append(field.getName());
                }else {
                    result.append(" ").append(argumentMark.value());
                }
            }
            OptionMark optionMark=field.getAnnotation(OptionMark.class);
            if(optionMark!=null){
                hasOptions=true;//记录一下，表示确实存在选项
            }
        }
        if(hasOptions)result.append(" [options]");
        if(selfIntroduce(true)!=null)result.append(" ").append(selfIntroduce(true));//添加自我介绍

        if(!inshort){//如果需要的是详细说明的话,要把每个选项的信息都打印出来
            result.append("\n");
            if(hasOptions)result.append("\nAvailable options:");
            for(Field field:clazz.getDeclaredFields()){
                OptionMark optionMark=field.getAnnotation(OptionMark.class);
                if(optionMark!=null){//对于每一个Option，都读取其信息。
                    result.append("\n   ");
                    if(!optionMark.shortName().equals("")){
                        result.append(" -").append(optionMark.shortName());
                    }
                    if(!optionMark.value().equals("")){
                        result.append(" --").append(optionMark.value());
                    }else{
                        result.append(" --").append(field.getName());
                    }
                    result.append(" ").append(optionMark.comment());
                }
            }
            if(selfIntroduce(false)!=null)
                result.append("\n ").append(selfIntroduce(false));
        }
        return result.toString();
    }

    public final void injectManagerObjectToCommandImplObject(CommandManager manager, CommandTriger triger) {
        this.manager=manager;
        this.triger=triger;
    }

}
