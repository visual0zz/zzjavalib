package com.zz.utils.threadunsafe.math;

import com.zz.utils.threadunsafe.math.exception.InvalidMatrixOperationException;
import com.zz.utils.threadunsafe.math.exception.UnmatchedMatrixSizeException;

import java.math.BigDecimal;
import java.math.BigInteger;

import static com.zz.utils.threadunsafe.math.Matrix.MatrixType.normal;
import static com.zz.utils.threadunsafe.math.Matrix.MatrixType.identity;
import static com.zz.utils.threadunsafe.math.Matrix.MatrixType.zero;
import static com.zz.utils.threadunsafe.math.Matrix.MatrixType.num;

public class Matrix {
    private BigDecimal[][] data;// 用于存储普通矩阵的数据，不储存特殊矩阵
    private int x,y;//普通矩阵时存储尺寸
    private BigDecimal value;//存储特殊矩阵的特殊值
    private MatrixType type;//矩阵类型

    // region ###--构造函数区--###
    public Matrix(String [][] in){
        x=in.length;y=in[0].length;
        value=new BigDecimal(in[0][0]);//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=new BigDecimal(in[i][j]);
            }
        type=normal;
    }
    public Matrix(int [][] in){
        x=in.length;y=in[0].length;
        value=new BigDecimal(in[0][0]);//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=new BigDecimal(in[i][j]);
            }
        type=normal;
    }
    public Matrix(long [][] in){
        x=in.length;y=in[0].length;
        value=new BigDecimal(in[0][0]);//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=new BigDecimal(in[i][j]);
            }
        type=normal;
    }
    public Matrix(float [][] in){
        x=in.length;y=in[0].length;
        value=new BigDecimal(in[0][0]);//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=new BigDecimal(in[i][j]);
            }
        type=normal;
    }
    public Matrix(double [][] in){
        x=in.length;y=in[0].length;
        value=new BigDecimal(in[0][0]);//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=new BigDecimal(in[i][j]);
            }
        type=normal;
    }
    public Matrix(BigInteger[][] in){
        x=in.length;y=in[0].length;
        value=new BigDecimal(in[0][0]);//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=new BigDecimal(in[i][j]);
            }
        type=normal;
    }
    public Matrix(BigDecimal [][] in){
        x=in.length;y=in[0].length;
        value=in[0][0];//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=in[i][j];
            }
        type=normal;
    }
    public Matrix(Matrix input){
        BigDecimal[][] in=input.data;
        x=in.length;y=in[0].length;
        value=in[0][0];//存储这个值没有用，只是用来验证输入数组的尺寸是不是合理的，过滤掉0*n的矩阵
        data =new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=in[i][j];
            }
        type=input.type;
    }
    public Matrix(int xSize,int ySize,BigDecimal preFillNum){
        if(xSize<=0 || ySize<=0) throw new IllegalArgumentException("0*0 matrix is invalid.");
        x=xSize;y=ySize;
        data=new BigDecimal[x][y];
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                data[i][j]=preFillNum;
            }
        type=normal;
    }
    private Matrix(){}
    private Matrix(int x,int y){
        type=normal;
        this.x=x;this.y=y;
        data=new BigDecimal[x][y];
    }
    private Matrix(BigDecimal multi){
        if( BigDecimalEqual(multi,new BigDecimal(0))){
            type= zero;
        }else if( BigDecimalEqual(multi,new BigDecimal(1)) ){
            type=identity;
        }else {
            type=num;
        }
        value=multi;
    }
    static public Matrix E=new Matrix(new BigDecimal(1));//单位矩阵
    static public Matrix O=new Matrix(new BigDecimal(0));//零矩阵
    //endregion


    //region ###--数乘运算区--###
    public Matrix multi(short num){
        return multi(new BigDecimal(num));
    }
    public Matrix multi(long num){
        return multi(new BigDecimal(num));
    }
    public Matrix multi(int num){
        return multi(new BigDecimal(num));
    }
    public Matrix multi(float num){
        return multi(new BigDecimal(num));
    }
    public Matrix multi(double num){ return multi(new BigDecimal(num)); }
    public Matrix multi(String num){ return multi(new BigDecimal(num)); }
    public Matrix multi(BigDecimal num){
        if(type==normal){
            return multiNormal(num);
        }
        else return new Matrix(value.multiply(num));
    }
    //endregion //数乘运算区


    //region ###--运算函数区--###
    public Matrix add(Matrix matrix){
        if(type==normal && matrix.type==normal) {//普通矩阵运算
            if (x != matrix.x || y != matrix.y)
                throw new UnmatchedMatrixSizeException("try to add " + x + "*" + y + " matrix to " + matrix.x + "*" + matrix.y + " matrix.");
            return addNormal(matrix);
        }else if(type==normal){//[]*E
            if(x!=y) throw new UnmatchedMatrixSizeException("try to add "+x+"*"+y+" matrix to a square matrix "+matrix.value+"E.");
            Matrix result=new Matrix();
            result.x=x;result.y=y;
            result.data=new BigDecimal[x][y];
            result.type=normal;
            for(int i=0;i<x;i++){
                for(int j=0;j<y;j++)
                    if(i==j)result.data[i][j]=data[i][j].add(matrix.value);//在对角线上加数
                    else result.data[i][j]=data[i][j];
            }
            return result;
        }else if(matrix.type==normal){//E*[]
            if(matrix.x!=matrix.y) throw new UnmatchedMatrixSizeException("try to add "+x+"*"+y+" matrix to a square matrix "+matrix.value+"E.");
            Matrix result=new Matrix();
            result.x=matrix.x;result.y=matrix.y;
            result.data=new BigDecimal[matrix.x][matrix.y];
            result.type=normal;
            for(int i=0;i<matrix.x;i++){
                for(int j=0;j<matrix.y;j++)
                    if(i==j)result.data[i][j]=matrix.data[i][j].add(value);//在对角线上加数
                    else result.data[i][j]=matrix.data[i][j];
            }
            return result;
        }
        return new Matrix(value.add(matrix.value));//两个都是特殊矩阵
    }
    public Matrix sub(Matrix matrix){
        return add(matrix.negate());
    }
    public Matrix multi(Matrix matrix){
        if(type==normal && matrix.type==normal){//两个都是普通矩阵
            if(this.y!=matrix.x)throw new UnmatchedMatrixSizeException("try to multi "+x+"*"+y+" matrix and "+matrix.x+"*"+matrix.y);
            return multiNormal(matrix);
        }else if(type!=normal && matrix.type!=normal)//两个都不是普通矩阵
            return new Matrix(value.multiply(matrix.value));
        else if(type==normal)return multi(matrix.value);
        else return matrix.multi(value);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass()==Matrix.class){
            Matrix m=(Matrix)obj;
            BigDecimal v;
            if(m.type==normal&& type==normal){//两个都是普通矩阵
                return equalNormal(m);
            }else if(m.type!=normal&& this.type!=normal){//两个都是特殊矩阵
                return BigDecimalEqual(value,m.value);
            }else {//一个是普通矩阵，一个是特殊矩阵
                if(m.type==normal){v=value;}
                else{v=m.value;m=this;}//保持m是普通矩阵 v是特殊矩阵值
                if(m.x!=m.y)return false;
                for(int i=0;i<m.x;i++)
                    for(int j=0;j<m.y;j++)
                    {
                        if(i==j && !BigDecimalEqual(m.data[i][j],v))return false;
                        else if(i!=j &&!BigDecimalEqual(m.data[i][j],new BigDecimal(0)))return false;
                    }
                return true;
            }

        }else if(obj.getClass()==Integer.class){return equals(new Matrix(new BigDecimal((Integer)obj)));}
        else if(obj.getClass()==Double.class){return equals(new Matrix(new BigDecimal((Double) obj)));}
        else if(obj.getClass()==Float.class){return equals(new Matrix(new BigDecimal((Float)obj)));}
        else if(obj.getClass()==Short.class){return equals(new Matrix(new BigDecimal((Short)obj)));}
        else if(obj.getClass()==Long.class){return equals(new Matrix(new BigDecimal((Long)obj)));}
        else if(obj.getClass()==BigDecimal.class){return equals(new Matrix(((BigDecimal)obj)));}
        else if(obj.getClass()==BigInteger.class){return equals(new Matrix(new BigDecimal((BigInteger)obj)));}
        else return false;
    }
    //endregion


    //region ###--内部运算区--###
    private Matrix addNormal(Matrix matrix){//将两个正常矩阵相加
        Matrix result=new Matrix();
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                result.data[i][j]=data[i][j].add(matrix.data[i][j]);
            }
        result.x=x;
        result.y=y;
        result.type=normal;
        return result;
    }
    public Matrix negate(){//得到负的矩阵
        Matrix result=new Matrix();
        if(type==normal){//普通矩阵就将每一个元素取反
            result.type=normal;
            result.data=new BigDecimal[x][y];
            result.x=x;result.y=y;
            for(int i=0;i<x;i++)
                for(int j=0;j<y;j++)
                    result.data[i][j]=data[i][j].negate();
            return result;
        }else {
            return new Matrix(value.negate());
        }
    }
    private Matrix multiNormal(Matrix matrix){//将两个正常矩阵相乘
        Matrix result=new Matrix(x,matrix.y);
        BigDecimal sum;
        for(int rowIndex1=0;rowIndex1<x;rowIndex1++)
            for(int colIndex2=0;colIndex2<matrix.y;colIndex2++)
            {
                sum=new BigDecimal(0);
                for(int colIndex1=0;colIndex1<y;colIndex1++){
                    sum=sum.add(data[rowIndex1][colIndex1].multiply(matrix.data[colIndex1][colIndex2]));
                }
                result.data[rowIndex1][colIndex2]=sum;
            }
        return result;
    }
    private Matrix multiNormal(BigDecimal num){//矩阵的数乘
        Matrix result=new Matrix(x,y);
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                result.data[i][j]=data[i][j].multiply(num);
            }
        return result;
    }
    private boolean equalNormal(Matrix matrix){
        if(x!=matrix.x||y!=matrix.y)return false;
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                if(!BigDecimalEqual(data[i][j],matrix.data[i][j])) return false;
            }
        return true;
    }
    //endregion

    //region ###--自体运算区--###
    public Matrix T(){//转置矩阵
        Matrix result=new Matrix(y,x);
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                result.data[j][i]=data[i][j];
            }
        return result;
    }

    /**
     * 计算矩阵的剩余矩阵
     * @param x_ 行序号 从1开始计数
     * @param y_ 列序号 从1开始计数
     * @return 剩余矩阵
     *
     * 对矩阵  [1,2,3]  使用cofactorMatrix(1,1)得到  [5,6]
     *        [4,5,6]                              [8,9]
     *        [7,8,9]
     */
    private Matrix cofactorMatrix(int x_,int y_){//得到计算余子式使用的那个子矩阵
        if(type!=normal)throw new InvalidMatrixOperationException("abstract matrix has no co-factor matrix.");
        if(x==0 || y==0 ||x==1 ||y==1|| x!=y )
            throw new InvalidMatrixOperationException(
                    "calculating co-factor matrix of a empty ,single-member or non-square matrix is invalid."
            );
        Matrix result=new Matrix(x-1,y-1);
        int targetx,targety;
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                if(i==x_ || j==y_)continue;
                if(i<x_)targetx=i;
                else targetx=i-1;
                if(j<y_)targety=j;
                else targety=j-1;
                result.data[targetx][targety]=data[i][j];
            }
        return result;
    }
    public BigDecimal determinant(){//求行列式
        if(type!=normal)throw new InvalidMatrixOperationException("abstract matrix has no determinant.");
        if(x==0 || y==0 || x!=y )
            throw new InvalidMatrixOperationException(
                    "calculating co-factor matrix of a empty ,single-member or non-square matrix is invalid."
            );
        if(x==1) return data[0][0];
        BigDecimal result=new BigDecimal(0);
        for(int i=0;i<x;i++){
            result=result.add( cofactorMatrix(i,0).determinant().multiply(new BigDecimal(i%2==0?1:-1)).multiply(data[i][0]));
        }
        return result;
    }
    public Matrix adjointMatrix(){//求伴随矩阵
        Matrix result =new Matrix(x,y);
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                result.data[i][j]=cofactorMatrix(i,j).determinant().multiply(new BigDecimal((i+j)%2==0?1:-1));
            }
        return result.T();
    }
    public Matrix inverse(){//求逆矩阵
        return adjointMatrix().multi(
                new BigDecimal("1.0").setScale(100,BigDecimal.ROUND_HALF_EVEN)
                        .divide(determinant(),BigDecimal.ROUND_HALF_EVEN));
    }
    //endregion

    private boolean BigDecimalEqual(BigDecimal a,BigDecimal b){//差小于一定精度就认为相等
        a=a.setScale(100,BigDecimal.ROUND_HALF_EVEN);
        if(a.subtract(b).abs().multiply(a.abs().add(b.abs())).compareTo(new BigDecimal("1E-90"))<0)return true;
        else return false;
    }

    enum MatrixType{
        normal //普通矩阵，x,y存储矩阵尺寸,data存储矩阵内容
        ,identity //单位矩阵
        ,zero //零矩阵
        ,num //整数和单位矩阵的积
    }



    //region ###--toString--###
    @Override
    public String toString() {return toString(0,BigDecimal.ROUND_UNNECESSARY);}
    private String toString(int digits,int mode) {//digits表示保留多少为小数
        if(type==normal){
            StringBuilder builder=new StringBuilder("<"+x+"*"+y+" matrix>\n");
            for(int i=0;i<x;i++)
            {
                if(i!=0)builder.append("\n");
                builder.append("[");
                for(int j=0;j<y;j++){
                    if(j!=0)builder.append(",\t");
                    if(mode!=BigDecimal.ROUND_UNNECESSARY)builder.append(data[i][j].setScale(digits,mode));
                    else builder.append(data[i][j]);
                }
                builder.append("]");
            }
            return builder.toString();
        }else {
            if(type==zero)return "O";
            else if(type==identity) return "E";
            else if(mode==BigDecimal.ROUND_UNNECESSARY)return value+"E";
            else return value.setScale(digits,mode)+"E";
        }
    }
    public String toStringHalfUp(int digits){return toString(digits,BigDecimal.ROUND_HALF_UP);}//四舍五入保留digits位小数
    public String toStringHalfEven(int digits){return toString(digits,BigDecimal.ROUND_HALF_EVEN);}//四舍六入五取偶保留digits位小数
    public String toStringHalfDown(int digits){return toString(digits,BigDecimal.ROUND_HALF_DOWN);}//五舍六入保留digits位小数
    //endregion


    public Matrix setScale(int newScale){
        if(type!=normal)throw new IllegalArgumentException("abstract matrix has no scale attribute.");
        Matrix result=new Matrix(x,y);
        for(int i=0;i<x;i++)
            for(int j=0;j<y;j++){
                result.data[i][j]=data[i][j].setScale(newScale,BigDecimal.ROUND_HALF_EVEN);
            }
        return result;
    }
    public BigDecimal getAt(int x,int y){//序号从1开始  1 2 3 ... 这样
        if(type==normal)
            return data[x-1][y-1];
        else if(x==y && x>0)
            return value;
        else
            return new BigDecimal(0);
    }
    public int getRowsNumber(){//这里的-1表示无限，E是一个抽象的矩阵，不是任何一个大小
        if(type==normal)
            return x;
        else throw new InvalidMatrixOperationException("abstract matrix are without size.");
    }
    public int getColmnsNum(){//这里的-1表示无限，E是一个抽象的矩阵，不是任何一个大小
        if(type==normal)
            return y;
        else throw new InvalidMatrixOperationException("abstract matrix are without size.");
    }
}
