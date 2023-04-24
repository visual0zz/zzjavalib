package com.zz.lib.idealogy.core;
import com.zz.lib.idealogy.interfaces.DropStrategy;
import java.util.List;
public interface World {
    /**
     * 向世界中添加物体
     * @param thing 物体
     */
    <T extends Thing> Id<T> put(Thing thing);

    /**
     * 从世界中删除物体
     * @param id 物体或者行为的id
     */
    <T extends Thing>void remove(Id<T> id);

    /**
     * 在世界中搜索物体
     * @param filter 物体的属性的范围
     * @return 属性值符合特定范围的所有物体的集合
     */
    <T extends Attribute<T>> List<Id<Thing>> searchThings(AttributeFilter<T> filter);

    /**
     * 向世界注册一个行为
     * @param action 行为
     * @param filter 行为的目标对象的属性描述
     * @param tickDelay 注册到几个tick之后，应当大于或等于1
     */
    <T extends Attribute<T>,R extends Action> Id<R> register(Action action, AttributeFilter<T> filter, long tickDelay);

    /**
     * 取消一个行为
     * @param id 行为id
     */
    <T extends Action>void cancel(Id<T> id);
    /**
     * 在世界中搜索行为
     * @param filter 行为的属性的范围
     * @return 属性值符合特定范围的所有行为的集合
     */
    <T extends Attribute<T>> List<Id<Action>> searchActions(AttributeFilter<T> filter);

    /**
     * 根据id获得世界中注册的物体或者行为
     * @param id id
     * @return 物体或者行为
     */

    <T extends Action & Thing> T get(Id<T> id);
    /**
     * 世界运行一帧
     * @param dropStrategy 帧内行为抛弃策略，为了保持帧运行的稳定和恒定时间，需要动态分析耗时来抛弃一部分行为不进行运行
     */
    void tick(DropStrategy dropStrategy);

}
