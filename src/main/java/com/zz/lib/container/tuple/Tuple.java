package com.zz.lib.container.tuple;
import com.zz.lib.common.exception.InvalidOperationException;
import java.io.Serializable;
import java.util.Arrays;
import com.zz.lib.common.CheckUtil;

public class Tuple implements Cloneable, Comparable<Tuple>, Serializable {
    private final static long serialVersionUID=1L;
    private final Object[]contents;
    protected Tuple( Object...vs) {
        CheckUtil.mustMatchSize(vs,2,30);
        contents=vs;
    }
    public int size(){
        return contents.length;
    }
    public Object getVn(int i){
        return contents[i-1];
    }
    private static <T> int compare(T o1, T o2) {
        if(o1==null && o2==null){
            return 0;
        }
        if(o1==null){
            return -1;
        }
        if(o2==null){
            return 1;
        }
        if(o1 instanceof Comparable){
            return  ((Comparable) o1).compareTo(o2);
        }
        throw new InvalidOperationException("not comparable.");
    }
    @Override
    public int compareTo(Tuple v) {
        for(int i=1,n = Math.min(this.size(), v.size()); i <= n; ++i) {
            int result = compare(this.getVn(i), v.getVn(i));
            if (result != 0) {
                return result;
            }
        }
        return Integer.compare(this.size(), v.size());
    }
    @Override
    public Tuple clone(){
        return new Tuple(this.contents);
    }
    public static <T1,T2> Tuple2<T1,T2> of(T1 v1,T2 v2){
        return new Tuple2(v1,v2);
    }
    public static <T1,T2,T3> Tuple3<T1,T2,T3> of(T1 v1,T2 v2,T3 v3){
        return new Tuple3(v1,v2,v3);
    }
    public static <T1,T2,T3,T4> Tuple4<T1,T2,T3,T4> of(T1 v1,T2 v2,T3 v3,T4 v4){
        return new Tuple4(v1,v2,v3,v4);
    }
    public static <T1,T2,T3,T4,T5> Tuple5<T1,T2,T3,T4,T5> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5){
        return new Tuple5(v1,v2,v3,v4,v5);
    }
    public static <T1,T2,T3,T4,T5,T6> Tuple6<T1,T2,T3,T4,T5,T6> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6){
        return new Tuple6(v1,v2,v3,v4,v5,v6);
    }
    public static <T1,T2,T3,T4,T5,T6,T7> Tuple7<T1,T2,T3,T4,T5,T6,T7> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7){
        return new Tuple7(v1,v2,v3,v4,v5,v6,v7);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8> Tuple8<T1,T2,T3,T4,T5,T6,T7,T8> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8){
        return new Tuple8(v1,v2,v3,v4,v5,v6,v7,v8);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9> Tuple9<T1,T2,T3,T4,T5,T6,T7,T8,T9> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9){
        return new Tuple9(v1,v2,v3,v4,v5,v6,v7,v8,v9);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> Tuple10<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10){
        return new Tuple10(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11> Tuple11<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11){
        return new Tuple11(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12> Tuple12<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12){
        return new Tuple12(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13> Tuple13<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13){
        return new Tuple13(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14> Tuple14<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14){
        return new Tuple14(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15> Tuple15<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15){
        return new Tuple15(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16> Tuple16<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16){
        return new Tuple16(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17> Tuple17<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17){
        return new Tuple17(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18> Tuple18<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18){
        return new Tuple18(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19> Tuple19<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19){
        return new Tuple19(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20> Tuple20<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20){
        return new Tuple20(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21> Tuple21<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21){
        return new Tuple21(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22> Tuple22<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22){
        return new Tuple22(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23> Tuple23<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23){
        return new Tuple23(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24> Tuple24<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24){
        return new Tuple24(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25> Tuple25<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24,T25 v25){
        return new Tuple25(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26> Tuple26<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24,T25 v25,T26 v26){
        return new Tuple26(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27> Tuple27<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24,T25 v25,T26 v26,T27 v27){
        return new Tuple27(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28> Tuple28<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24,T25 v25,T26 v26,T27 v27,T28 v28){
        return new Tuple28(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,v28);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28,T29> Tuple29<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28,T29> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24,T25 v25,T26 v26,T27 v27,T28 v28,T29 v29){
        return new Tuple29(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,v28,v29);
    }
    public static <T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28,T29,T30> Tuple30<T1,T2,T3,T4,T5,T6,T7,T8,T9,T10,T11,T12,T13,T14,T15,T16,T17,T18,T19,T20,T21,T22,T23,T24,T25,T26,T27,T28,T29,T30> of(T1 v1,T2 v2,T3 v3,T4 v4,T5 v5,T6 v6,T7 v7,T8 v8,T9 v9,T10 v10,T11 v11,T12 v12,T13 v13,T14 v14,T15 v15,T16 v16,T17 v17,T18 v18,T19 v19,T20 v20,T21 v21,T22 v22,T23 v23,T24 v24,T25 v25,T26 v26,T27 v27,T28 v28,T29 v29,T30 v30){
        return new Tuple30(v1,v2,v3,v4,v5,v6,v7,v8,v9,v10,v11,v12,v13,v14,v15,v16,v17,v18,v19,v20,v21,v22,v23,v24,v25,v26,v27,v28,v29,v30);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tuple)) return false;
        Tuple tuple = (Tuple) o;
        return Arrays.equals(contents, tuple.contents);
    }
    @Override
    public int hashCode() {
        return Arrays.hashCode(contents);
    }
    @Override
    public String toString(){
        StringBuilder builder=new StringBuilder("<");
        for(Object obj:contents){
            builder.append(obj).append(',');
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append('>');
        return builder.toString();
    }
}