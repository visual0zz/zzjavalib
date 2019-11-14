package com.zz.utils.math;

public class InfiniteTable<T,Y> {


    enum InfiniteTableType{
        generator,//生成器，不存储实际东西
        number,//都是同一个数
        cycle,//循环平铺
        finite;//有限尺寸
    }
    T a;
}
