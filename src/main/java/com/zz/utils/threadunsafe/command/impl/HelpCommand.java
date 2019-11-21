package com.zz.utils.threadunsafe.command.impl;

import com.zz.utils.threadunsafe.basicwork.ShellColor;
import com.zz.utils.threadunsafe.command.ArgumentMark;
import com.zz.utils.threadunsafe.command.Command;

public class HelpCommand extends Command {

    @ArgumentMark
    public String command;

    @Override
    public void run() {
        if(command==null) {
            System.out.println(ShellColor.sky + ShellColor.hightlight + "Available commands" + ShellColor.clear + ":");
            for (CommandTriger triger : manager.commands) {
                System.out.println(triger.commandObject.generateHelpInformationThroughReflection(true));
            }
        }else {
            for(CommandTriger triger:manager.commands){
                if(triger.commandkey.equals(command))
                    System.out.println(triger.commandObject.generateHelpInformationThroughReflection(false));
            }
        }
    }

    @Override
    public String commandLine() {
        return "help";
    }

    @Override
    public String selfIntroduce(boolean inShort) {
        if(inShort)return "获取指令的帮助信息";
        else return "直接调用help可以获得可用的指令列表，通过输入特定的指令嗯字符串可以获得对应指令的详细信息。";
    }
}
