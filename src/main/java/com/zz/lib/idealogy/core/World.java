package com.zz.lib.idealogy.core;
import com.zz.lib.idealogy.interfaces.DropStrategy;
import java.util.List;
public interface World {
    void put(Thing thing);
    void drop(Id id);
    List<Thing> searchThings(Attribute attribute);
    <T extends Attribute> List<Thing> searchThings(AttributeFilter<T> filter);
    <T extends Attribute> List<Thing> searchAction(AttributeFilter<T> filter);
    <T extends Attribute> boolean addAction(Action action, AttributeFilter<T> filter);
    <T extends Attribute> boolean addAction(Action action, AttributeFilter<T> filter, long tickDelay);
    <T extends Attribute> void cancelAction(AttributeFilter<T> filter);
    void tick(DropStrategy dropStrategy);

}
