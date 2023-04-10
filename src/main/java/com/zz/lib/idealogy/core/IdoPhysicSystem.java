package com.zz.lib.idealogy.core;

import java.util.Collection;
import java.util.Set;

public interface IdoPhysicSystem extends IdoObject {
    void setStatus(Collection<IdoObject> objects);
    Set<IdoObject> getStatus();
    void step();
}
