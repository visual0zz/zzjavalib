package com.zz.lib.container;
import com.zz.lib.common.CheckUtil;
import java.math.BigInteger;
import java.util.Arrays;

public class Vector {
    BigInteger [] components;
    private Vector(){}
    public static Vector of(int ... components){
        Vector vector =new Vector();
        vector.components=new BigInteger[components.length];
        for(int i=0;i<components.length;i++){
            vector.components[i]= BigInteger.valueOf(components[i]);
        }
        return vector;
    }
    public static Vector of(long ... components){
        Vector vector =new Vector();
        vector.components=new BigInteger[components.length];
        for(int i=0;i<components.length;i++){
            vector.components[i]= BigInteger.valueOf(components[i]);
        }
        return vector;
    }
    public static Vector of(String ... components){
        Vector vector =new Vector();
        vector.components=new BigInteger[components.length];
        for(int i=0;i<components.length;i++){
            vector.components[i]= new BigInteger(components[i]);
        }
        return vector;
    }
    public static Vector of(BigInteger ... components){
        Vector vector =new Vector();
        vector.components=new BigInteger[components.length];
        for(int i=0;i<components.length;i++){
            CheckUtil.mustNotNull(components[i]);
            vector.components[i]= components[i];
        }
        return vector;
    }
    BigInteger innerProduct(Vector otherVector){
        CheckUtil.mustSameSize(components,otherVector.components);
        BigInteger result=BigInteger.valueOf(0);
        for(int i=0;i<components.length;i++){
            result.add(components[i].multiply(otherVector.components[i]));
        }
        return result;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return Arrays.equals(components, vector.components);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(components);
    }
}
