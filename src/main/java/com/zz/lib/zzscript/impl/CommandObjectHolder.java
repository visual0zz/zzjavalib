package com.zz.lib.zzscript.impl;

import com.zz.lib.zzscript.Command;

/**
 * 指令对象的代理对象，用于处理指令的参数重载和进行参数格式校验
 */
public final class CommandObjectHolder {
    private final Command command;
    public CommandObjectHolder(Command command){
        this.command=command;
    }

    /**
     * 调用指令
     * @param arg 指令的参数
     * @return 指令的返回值
     */
    public Argument invoke(Argument arg){
        return null;
    }
}
