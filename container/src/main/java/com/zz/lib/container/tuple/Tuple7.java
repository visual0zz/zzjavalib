package com.zz.lib.container.tuple;


public final class Tuple7 <T1,T2,T3,T4,T5,T6,T7> extends Tuple{
    public Tuple7(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7) {
        super(v1,v2,v3,v4,v5,v6,v7);
    }
    public T1 getV1(){
        return (T1)getVn(1);
    }
    public T2 getV2(){
        return (T2)getVn(2);
    }
    public T3 getV3(){
        return (T3)getVn(3);
    }
    public T4 getV4(){
        return (T4)getVn(4);
    }
    public T5 getV5(){
        return (T5)getVn(5);
    }
    public T6 getV6(){
        return (T6)getVn(6);
    }
    public T7 getV7(){
        return (T7)getVn(7);
    }
}