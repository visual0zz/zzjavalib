package com.zz.lib.common.util;

public enum SpiPriority implements Comparable<SpiPriority>{
    DEFAULT(50),NORMAL(30),SPECIAL(10);
    private int priority;//数值越小约优先
    SpiPriority(int priority){
        this.priority=priority;
    }
}
