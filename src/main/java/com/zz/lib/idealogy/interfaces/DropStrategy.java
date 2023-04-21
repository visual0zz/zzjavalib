package com.zz.lib.idealogy.interfaces;

import com.zz.lib.idealogy.core.Action;
import com.zz.lib.idealogy.core.World;

public interface DropStrategy {
    boolean beforeAction(World world, Action action, long nanoTime);
    void afterAction(World world, Action action, long nanoTime);
}
