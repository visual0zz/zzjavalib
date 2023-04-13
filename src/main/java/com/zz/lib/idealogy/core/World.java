package com.zz.lib.idealogy.core;

public interface World {
    void add(Object object);
    Action act(Object source, Action action,Object target);
}
