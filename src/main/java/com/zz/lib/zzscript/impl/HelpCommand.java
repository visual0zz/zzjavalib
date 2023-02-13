package com.zz.lib.zzscript.impl;

import com.zz.lib.zzscript.*;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }
    @Run("查看有哪些指令可用")
    public String listCommands(){
        return "";
    }
    @Run("查看某个指令的详细功能")
    public String helpCommand(@Req("指令名称") String command){
        return "";
    }
}
