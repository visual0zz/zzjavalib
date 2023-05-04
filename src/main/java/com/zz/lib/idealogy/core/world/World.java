package com.zz.lib.idealogy.core.world;

import com.zz.lib.idealogy.util.interfaces.AttributeHolder;
import com.zz.lib.idealogy.util.interfaces.DropStrategy;

import java.util.List;

public class World {
    /**
     * 向世界中添加物体
     *
     * @param thing 物体
     */
    public <T extends Thing> Id<Thing> put(T thing) {
        return null;
    }

    /**
     * 从世界中删除物体
     *
     * @param id 物体或者行为的id
     */
    public void remove(Id<Thing> id) {
    }

    /**
     * 在世界中搜索物体
     *
     * @param filter 物体的属性的范围
     * @return 属性值符合特定范围的所有物体的集合
     */
    public List<Id<Thing>> searchThings(AttributeFilter filter) {
        return null;
    }

    /**
     * 向世界注册一个行为
     *
     * @param action    行为
     * @param filter    行为的目标对象的属性描述
     * @param tickDelay 注册到几个tick之后，应当大于或等于1
     */
    public Id<Action> register(Action action, AttributeFilter filter, long tickDelay) {
        return null;
    }

    /**
     * 取消一个行为
     *
     * @param id 行为id
     */
    public void cancel(Id<Action> id) {
    }

    /**
     * 在世界中搜索行为
     *
     * @param filter 行为的属性的范围
     * @return 属性值符合特定范围的所有行为的集合
     */
    public List<Id<Action>> searchActions(AttributeFilter filter) {
        return null;
    }

    /**
     * 根据id获得世界中注册的物体或者行为
     *
     * @param id id
     * @return 物体或者行为
     */

    public <T extends AttributeHolder> T get(Id<T> id) {
        return null;
    }

    /**
     * 世界运行一帧
     *
     * @param dropStrategy 帧内行为抛弃策略，为了保持帧运行的稳定和恒定时间，需要动态分析耗时来抛弃一部分行为不进行运行
     */
    public void tick(DropStrategy dropStrategy) {
    }

}
