package com.zz.utils.command.impl;

import com.zz.utils.command.exceptions.CommandLineFormatException;
import com.zz.utils.command.exceptions.CommandOptionException;

import java.util.ArrayList;

/**
 * 负责对命令行进行初步的格式解析
 */
public class CommandLine {//负责解析命令行的格式
    // generateHelpInformationThroughReflection -a b  -cd -- -123
    ArrayList<String> data;//       -a      b      -cd     -123
    ArrayList<Boolean> isOption;//  true    false   true    false

    public String getCommandType() {
        return commandType;
    }

    String commandType;         //  generateHelpInformationThroughReflection

    OptionStringSet opSet;
    public CommandLine(String[] args){
        data=new ArrayList<>();
        isOption=new ArrayList<>();

        boolean nextForceArgument=false;
        for(String str:args){
            if(nextForceArgument){
                readArgument(str);
                nextForceArgument=false;
            }else if(str.equals("--")){// --
                nextForceArgument=true;//-- 后的一个参数强制视为普通字符串，不是选项
            }else if(str.length()<=1){ // - 或者 h
                readArgument(str);
            }else if(str.substring(0,2).equals("--")){// --generateHelpInformationThroughReflection
                readOption(str);
            }else if(str.charAt(0)=='-'){ // -h
                readOption(str);
            }else {
                readArgument(str);
            }
        }
        if(data.size()!=isOption.size())throw new RuntimeException("出大问题了");
        buildOptionSet();
    }

    void buildOptionSet(){
        opSet=new OptionStringSet();
        for(int i=0;i<data.size();i++){
            if(isOption.get(i)){//递归每个Option字符串
                String str=data.get(i);
                if(str.matches("^--.*$")){
                    opSet.addLong(str.substring(2));
                }else if(str.matches("^-.*$")){
                    opSet.addShorts(str.substring(1));
                }else throw new RuntimeException("出大问题了");
            }
        }
    }
    void readOption(String option){
        if(option.length()>=3&&option.substring(0,2).equals("--")&&option.contains("=")){
            //使用等号格式给选项赋值 --from=/etc/source
            int index=option.indexOf('=');
            data.add(option.substring(0,index));
            isOption.add(true);
            if(index==option.length()-1)
                return;
            else {//使用空格分割选项和选项的值  --from /etc/source
                data.add(option.substring(index+1));
                isOption.add(false);
            }
        }else {
            data.add(option);
            isOption.add(true);
        }
    }
    void readArgument(String argument){
        if(commandType ==null)
            commandType =argument;
        else {
            data.add(argument);
            isOption.add(false);
        }
    }


    /**
     * @throws CommandLineFormatException
     * @throws CommandOptionException
     */
    public void writeDataToTriger(CommandTriger triger) throws CommandLineFormatException, IllegalAccessException {
        int length=data.size();
        int totalOptionCount=0;
        int argumentsSize=triger.arguments.size();
        int argumentsCount=0;
        for(int i=0;i<length;i++){
            String str= data.get(i);
            Boolean isoption=isOption.get(i);
            if(isoption){
                String candidateArgument=getNextArgument(i);
                int optionCount=0;
                for(Option op:triger.options){
                    if(op.readArgument(str,candidateArgument))optionCount++;
                }
                totalOptionCount+=optionCount;
                if(totalOptionCount>1)throw new CommandLineFormatException("格式错误，"+str+"选项中有多个需要参数的选项，发生冲突。");
            }else {
                if(totalOptionCount==1){
                    totalOptionCount=0;
                    continue;//如果参数已经被之前的选项读取了，就忽略跳过
                }
                else {
                    totalOptionCount=0;
                    argumentsCount++;
                    if(argumentsCount<=argumentsSize)
                        triger.arguments.get(argumentsCount-1).set(triger.commandObject,str);
                }
            }
        }
        if(argumentsCount>argumentsSize)throw new CommandLineFormatException("至多需要"+argumentsSize+"个参数,你传入了"+argumentsCount+"个参数。");
        for(Option op:triger.options)op.writeObject(triger.commandObject);
    }
    private String getNextArgument(int index){
        int length=data.size();
        for(int i=index+1;i<length;i++){
            if(isOption.get(i))continue;
            else return data.get(i);
        }
        return null;
    }
    @Override
    public String toString() {
        return "CommandLine{ data="+data+" , isOption="+isOption+" , commandType="+ commandType +" }";
    }
}
