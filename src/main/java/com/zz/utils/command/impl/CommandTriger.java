package com.zz.utils.command.impl;

import com.zz.utils.command.ArgumentMark;
import com.zz.utils.command.Command;
import com.zz.utils.command.OptionMark;
import com.zz.utils.command.exceptions.CommandClassReadException;
import com.zz.utils.command.exceptions.CommandLineFormatException;
import com.zz.utils.command.exceptions.CommandOptionException;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * 负责解析指令并且触发相应的指令实现类
 * 一个triger对象对应一个Command对象，triger负责解析、储存、验证和注入Command对象定义的参数格式。
 */

public class CommandTriger {

    public CommandTriger setManager(CommandManager manager) {
        this.manager = manager;
        commandObject.injectManagerObjectToCommandImplObject(this.manager,this);//将自身和管理类的实例注入命令功能对象，方便命令之间互相调用。
        return this;
    }
    public CommandManager manager;//引用自己所属的管理者对象

    public CommandTriger(Class commandImplClass) throws CommandClassReadException, CommandOptionException {
        commandClass =commandImplClass;
        try {
            commandObject = (Command) commandImplClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CommandClassReadException("读取指令实现类"+commandImplClass.getName()+"时失败。",e);
        }
        catch (ClassCastException e){
            throw new CommandClassReadException(commandImplClass.getName()+"并没有实现Command接口，不是有效的指令实现类");
        }

        for(Field field: commandClass.getDeclaredFields()){//遍历目标类每个被@OptionMark修饰的field，建立对应的Option
            if(field.getAnnotation(OptionMark.class)!=null){//选项
                options.add(new Option(field));
            }else if(field.getAnnotation(ArgumentMark.class)!=null){//参数
                if(!(field.getType().isAssignableFrom(String.class)&&String.class.isAssignableFrom(field.getType())))
                    throw new CommandOptionException("@ArgumentMark修饰了一个不是String的变量" +
                            field.getDeclaringClass()+"."+
                            field.getName());
                arguments.add(field);//缓存每个field
            }
        }

        commandkey =commandObject.commandLine();//读取并暂存指令字符串
        if(commandkey ==null|| commandkey.isEmpty())
            throw new CommandClassReadException(commandImplClass.getName()+".commandLine()返回值为空白");
        buildOptionSet();
    }

    String commandkey;//指令的标识字符串

    Class commandClass;//目标指令实现类
    Command commandObject;//目标指令实现类的实例
    ArrayList<Option> options=new ArrayList<>();//用于储存所有选项的集合
    ArrayList<Field> arguments=new ArrayList<>();

    OptionStringSet opSet;
    void buildOptionSet(){
        opSet=new OptionStringSet();
        for(Option op:options)
            opSet.addOption(op);
    }
    CommandLine line;
    public boolean isMatch(String []args){//参数数组是否匹配本指令
        if(args.length == 0)return false;
        String commandline=new CommandLine(args).commandType;
        if(commandline==null)commandline="";
        return commandline.equals(commandkey);
    }

    public void readCommandLine(String []args) throws CommandLineFormatException, IllegalAccessException {
        //if(!isMatch(args))throw new IllegalAccessError("无法读取，这个指令序列不是本处理器应该处理的类型。");
        line=new CommandLine(args);
        OptionStringSet subset=line.opSet.sub(this.opSet);
        if(!subset.isEmpty())throw new CommandLineFormatException("发现无法识别的选项"+subset);
        line.writeDataToTriger(this);
    }
    public void triger(){
        commandObject.run();
    }

    public void clean() throws IllegalAccessException {//清空储存的指令数据，但保留Option格式数据
        for(Option op:options)op.clean();
        try{
            for(Field arg:arguments){
                    arg.set(commandObject,null);
            }
            for(Option op:options){
                if(op.field.getType().isAssignableFrom(String.class)&&String.class.isAssignableFrom(op.field.getType()))
                    op.field.set(commandObject,null);
                else
                    op.field.setBoolean(commandObject,false);
            }
        }catch (IllegalAccessException e){
            throw new RuntimeException(this.commandkey+"指令的定义类存在非public选项变量，无法外部注入值。",e);
        }
    }
    @Override
    public String toString() {
        return "CommandTriger{ commandClass="+commandClass+
                " ,options="+options+
                " ,max arguments:"+arguments.size()+
                " , commandkey="+commandkey+
                " }";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return toString().equals(obj.toString());
    }
}

