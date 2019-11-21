package com.zz.utils.threadunsafe.command.impl;

import com.zz.utils.threadunsafe.command.Command;
import com.zz.utils.threadunsafe.command.OptionMark;
import com.zz.utils.threadunsafe.command.exceptions.CommandLineFormatException;
import com.zz.utils.threadunsafe.command.exceptions.CommandOptionException;

import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Option{//指令中的选项
    Option(Field field) throws CommandOptionException {//根据被@CommandOption修饰的Field提供的信息，建立一个选项
        this.field=field;
        OptionMark annotation=field.getAnnotation(OptionMark.class);

        if(annotation==null) throw new CommandOptionException("一个不被@CommandOption修饰的Field "+field.getDeclaringClass().getName()+"."+
                field.getName()+"被用于构建选项。");

        fullName=annotation.value();
        if(fullName.equals(""))fullName=field.getName();//如果注解中没有提供全名，就使用field本身的名字作为选项全名
        shortName=annotation.shortName();if(shortName.equals(""))shortName=null;//简称不存在

        if(String.class.isAssignableFrom(field.getType())&&field.getType().isAssignableFrom(String.class)){
            //如果这个Field是一个String类型
            withArgument=true;
        }else if(boolean.class.isAssignableFrom(field.getType())&&field.getType().isAssignableFrom(boolean.class)) {
            //如果这个Field是一个boolean类型
            withArgument=false;
        }
        else{
            throw new CommandOptionException(field.getDeclaringClass().getName()+"."+
                    field.getName()+"不是String或者boolean类型。");
        }
        checkNameFormat();
    }

    Field field;//保存目标成员变量位置的引用
    String shortName;//选项简写，有可能是null
    String fullName;//选项全称，不可能是null
    boolean withArgument;//是一个布尔型选项还是一个有字符串的选项

    boolean boolValue; //用于暂存从命令行中读取到的选项的值
    String strValue;

    boolean written =false;//表示已经被写入过。如果再次写入应该报错
    /**
     * 从字符串中分析出选项的值
     * @param optionString 选项字符串
     * @param argCandidate 选项字符串之后的一个字符串，有可能是选项的参数
     * @return 如果afterString确实作为参数被使用了就返回true，否则返回false
     */
    public boolean readArgument(String optionString, String argCandidate) throws CommandLineFormatException {
        //System.out.println("readArgument(optionString="+optionString+" ,argCandidate="+argCandidate+" ) field="+field.getName());
        if(optionString.substring(0,2).equals("--")){//长格式参数
            if(optionString.substring(2).equals(fullName)){
                if(written)throw new CommandLineFormatException("选项"+fullName+"发生了重复");
                if(withArgument){
                    strValue=argCandidate;//System.out.println(1);
                    written=true;
                    return true;
                }else {
                    boolValue=true;//System.out.println(2);
                    written=true;
                    return false;
                }
            }else {
                return false;
            }
        }
        else if(optionString.charAt(0)=='-'){//短格式参数
            if(shortName!=null&&optionString.contains(shortName)){
                if(written)throw new CommandLineFormatException("选项"+fullName+"发生了重复");
                if(withArgument){
                    strValue=argCandidate;//System.out.println(strValue);
                    written=true;
                    return true;
                }else {
                    boolValue=true;//System.out.println(4);
                    written=true;
                    return false;
                }
            }else {
                return false;
            }
        }
        else {
            throw new CommandLineFormatException("尝试使用无效参数字符串来匹配Option:"+optionString);
        }

    }

    /**
     * @return 返回的是args的index值，表示对应字符串已经被自己作为选项的参数读取走了，已经失效了。为-1表示没有提取任何参数
     */
    //@Deprecated
    public void writeObject(Command command) throws IllegalAccessException {//从字符串数组中分析出信息并按照选项格式写入对应command对象。
        if(field.getDeclaringClass()!=command.getClass())throw new RuntimeException("无法写入对象，类型不正确。");

        if(!withArgument){//如果是布尔型选项
            field.setBoolean(command,boolValue);
        }else {//如果是有内容的选项
            field.set(command,strValue);//System.out.println(strValue);
        }
    }


    private void checkNameFormat() throws CommandOptionException {//检查选项名字格式是否符合
        if(shortName!=null){
            if(shortName.length()!=1)throw new CommandOptionException(field.getDeclaringClass().getName()+"."+
                    field.getName()+"选项简称 \""+shortName+"\" 长度不对");

            if(!Character.isLetterOrDigit(shortName.charAt(0)))throw new CommandOptionException(field.getDeclaringClass().getName()+"."+
                    field.getName()+"选项简称 \""+shortName+"\" 不是一个字母");
        }

        String regex="^[a-zA-Z0-9\\-_\u4e00-\u9fa5]+$";//判断全名是否符合格式的正则表达式
        Matcher match=Pattern.compile(regex).matcher(fullName);
        if(!match.matches())throw new CommandOptionException(field.getDeclaringClass().getName()+"."+
                field.getName()+"选项全名"+fullName+"不符合格式");
    }
    public void clean(){//清理命令行数据 但保留格式数据
        boolValue=false;
        strValue=null;
        written =false;
    }
    @Override
    public String toString() {
        return "Option{ field="+field.getDeclaringClass().getSimpleName()+"."+field.getName()+" " +
                ", shortName="+shortName+" " +
                ", fullName="+fullName+" " +
                ", withArgument="+withArgument+" }";
    }
}
