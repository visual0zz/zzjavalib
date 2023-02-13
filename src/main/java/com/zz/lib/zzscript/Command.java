package com.zz.lib.zzscript;

public abstract class Command {
    private CommandManager manager;
    private String name;
    public Command(String name) {
        this.name=name;
    }
    public CommandManager getManager() {
        return manager;
    }
}
