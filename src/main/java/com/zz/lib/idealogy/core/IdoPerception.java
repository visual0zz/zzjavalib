package com.zz.lib.idealogy.core;

import java.util.Collection;
import java.util.Set;

/**
 * 直觉，负责将实在界的实体转化为符号
 */
public interface IdoPerception extends IdoObject{
    Set<IdoWord> meditate(IdoObject ...objects);
    Set<IdoWord> meditate(Collection<IdoObject> objects);
    IdoWord meditate(IdoObject object);
    IdoWord meditate();
}
