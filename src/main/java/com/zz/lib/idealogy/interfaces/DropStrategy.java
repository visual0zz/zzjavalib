package com.zz.lib.idealogy.interfaces;

import com.zz.lib.idealogy.core.Action;
import com.zz.lib.idealogy.core.World;

public interface DropStrategy {
    /**
     * 动作执行前切面
     * @param world 世界
     * @param action 动作
     * @param tickTime 从本次tick开始过去的时间
     * @return true-此Action应该被执行 false-此Action应该被忽略
     */
    boolean beforeAction(World world, Action action, long tickTime);

    /**
     * 动作执行后切面
     * @param world 世界
     * @param action 动作
     * @param tickTime 从本次tick开始过去的时间
     * @param actionTime 从本次动作开始过去的时间
     */
    void afterAction(World world, Action action, long tickTime,long actionTime);

    /**
     * 是否抛弃本tick剩下的所有action
     * @param world 世界
     * @param tickTime 从本次tick开始过去的时间
     * @return true-抛弃剩余所有action，结束tick
     */
    boolean abortRemains(World world, long tickTime);
}
