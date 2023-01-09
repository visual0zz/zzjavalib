package com.zz.utils.threadunsafe.command.impl;


import com.zz.lib.os.constant.ShellColorConst;
import com.zz.utils.threadunsafe.command.Command;
import com.zz.utils.threadunsafe.command.exceptions.CommandClassReadException;
import com.zz.utils.threadunsafe.command.exceptions.CommandLineFormatException;
import com.zz.utils.threadunsafe.command.exceptions.CommandOptionException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import static java.lang.System.exit;

public class CommandManager {
    HashSet<CommandTriger> commands;
    boolean initialized =false;
    public void init(String [] packageNames){//读取包名下所有指令实现类。
        commands=new HashSet<>();
        PackageScanner scanPackage = new PackageScanner();
        for(String packageName:packageNames)
            scanPackage.addPackage(packageName);
        scanPackage.addPackage("com.zz.utils.threadunsafe.command.impl");//将预定义的指令添加进来

        scanPackage.setFilter(c-> Command.class.isAssignableFrom(c)&&!Modifier.isAbstract(c.getModifiers()));
        //所有实现了Command接口的非虚类
        scanPackage.setListener(clazz->commands.add(new CommandTriger(clazz).setManager(this)));
        try {
            scanPackage.scan();
        } catch (CommandOptionException | CommandClassReadException | IOException e) {
            e.printStackTrace();
            exit(1);
        }
        //System.out.println(commands);
        initialized =true;
    }
    public void init(String packageName){init(new String[]{packageName});}

    public void excuteCommand(String []args) throws CommandLineFormatException {
        if(!initialized)throw new RuntimeException("CommandManager没有初始化不能使用");
        for(CommandTriger triger:commands) {
            try {
                triger.clean();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                exit(1);//无法清理是严重错误
            }
        }

        boolean done=false;//是否成功执行指令
        try{
            for(CommandTriger command:commands){
                if(command.isMatch(args)){
                    command.readCommandLine(args);
                    command.triger();
                    done=true;
                    break;
                }
            }
            if(!done)throw new CommandLineFormatException("找不到对应的指令:"+new CommandLine(args).getCommandType());
        }catch (IllegalAccessException e) {
            e.printStackTrace();
            exit(1);
        }
    }
    public void commandCycle(InputStream inputStream, OutputStream erroOut, String prompt){
        if(!initialized)throw new RuntimeException("CommandManager没有初始化不能使用");
        Scanner in=new Scanner(inputStream);

        System.out.print(prompt);
        while(in.hasNextLine()){
            String line=in.nextLine();
            try {
                excuteCommand((String[]) split(line));
            } catch (CommandLineFormatException e) {
                new PrintStream(erroOut).println(ShellColorConst.red+e.getMessage()+ ShellColorConst.clear);
            }
            System.out.print(prompt);
        }
    }
    public void commandCycle(String prompt){
        commandCycle(System.in,System.out,prompt);}
    public void commandCycle(){
        commandCycle("\033[32m\033[01mPlease input\033[37m:>\033[0m ");}


    public CommandManager(){}
    public CommandManager(String []packageNames){init(packageNames);}
    public CommandManager(String packageName){init(packageName);}

    enum State{ normal,single_quote,double_quote,slash,single_quote_slash,double_quote_slash}
    public String[] split(String line) throws CommandLineFormatException {
        ArrayList<String> result=new ArrayList<>();//储存最终结果
        char[] buf=line.toCharArray();
        StringBuilder res=new StringBuilder();//暂存字符串

        State state=State.normal;
        for(char c:buf){
            if(c<0x20||c==0x7f)c=' ';//把控制字符全都替换成空格，要不然老是出bug
            switch (state){
                case normal:
                    switch (c){
                        case ' ':
                            String tmp=res.toString();//如果暂存区积累了一段字符就加入结果集合里面
                            if(!tmp.equals(""))result.add(tmp);
                            res=new StringBuilder();
                            break;
                        case '\"':
                            state=State.double_quote;
                            break;
                        case '\'':
                            state=State.single_quote;
                            break;
                        case '\\':
                            state=State.slash;
                            break;
                        default:
                            res.append(c);
                    }
                    break;
                case single_quote:
                    if(c=='\'')
                        state=State.normal;
                    else if(c=='\\')
                        state=State.single_quote_slash;
                    else
                        res.append(c);
                    break;
                case double_quote:
                    if(c=='\"')
                        state=State.normal;
                    else if(c=='\\')
                        state=State.double_quote_slash;
                    else
                        res.append(c);
                    break;
                case slash:
                    switch(c){//转意字符解析
                        case ' ':
                            res.append(' ');
                            break;
                        case 't':
                            res.append('\t');
                            break;
                        case 'n':
                            res.append('\n');
                            break;
                        case '\\':
                            res.append('\\');
                            break;
                        case '\"':
                            res.append('\"');
                            break;
                        case '\'':
                            res.append('\'');
                            break;
                        default:
                            throw new CommandLineFormatException("发现无法识别的转意字符: \\"+c);
                    }
                    state=State.normal;
                    break;
                case single_quote_slash:
                    switch(c){//转意字符解析
                        case ' ':
                            res.append(' ');
                            break;
                        case 't':
                            res.append('\t');
                            break;
                        case 'n':
                            res.append('\n');
                            break;
                        case '\\':
                            res.append('\\');
                            break;
                        case '\"':
                            res.append('\"');
                            break;
                        case '\'':
                            res.append('\'');
                            break;
                        default:
                            throw new CommandLineFormatException("发现无法识别的转意字符: \\"+c);
                    }
                    state=State.single_quote;
                    break;
                case double_quote_slash:
                    switch(c){//转意字符解析
                        case ' ':
                            res.append(' ');
                            break;
                        case 't':
                            res.append('\t');
                            break;
                        case 'n':
                            res.append('\n');
                            break;
                        case '\\':
                            res.append('\\');
                            break;
                        case '\"':
                            res.append('\"');
                            break;
                        case '\'':
                            res.append('\'');
                            break;
                        default:
                            throw new CommandLineFormatException("发现无法识别的转意字符: \\"+c);
                    }
                    state=State.double_quote;
                    break;
            }
        }
        String tmp=res.toString();//如果暂存区积累了一段字符就加入结果集合里面
        if(!tmp.equals(""))result.add(tmp);

        String[]ret=new String[result.size()];
        for(int i=0;i<ret.length;i++)
            ret[i]=result.get(i);
        return ret;
    }
}
