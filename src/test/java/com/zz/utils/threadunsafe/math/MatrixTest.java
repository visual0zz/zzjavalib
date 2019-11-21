package com.zz.utils.threadunsafe.math;

import com.zz.utils.threadunsafe.math.exception.InvalidMatrixOperationException;
import com.zz.utils.threadunsafe.math.exception.UnmatchedMatrixSizeException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class MatrixTest {

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testInverse(){
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,0}});
    System.out.println("matrix="+matrix);
    System.out.println("matrix.adjoint="+matrix.adjointMatrix());
    System.out.println("matrix.inverse="+matrix.inverse());
    System.out.println("matrix*matrix.inverse="+matrix.multi(matrix.inverse()).setScale(0));
    assertTrue(matrix.multi(matrix.inverse()).equals(Matrix.E));
  }

  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testGetAt(){
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
    System.out.println(matrix.getAt(0,0));
  }

  @Test(expected = InvalidMatrixOperationException.class)
  public void testDeterminantException(){
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6}});
    matrix.determinant();
  }
  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testDeterminantException1(){
    Matrix matrix=new Matrix(new int[][]{{}});
    System.out.println(matrix);
    matrix.determinant();
  }
  @Test(expected = ArrayIndexOutOfBoundsException.class)
  public void testDeterminantException2(){
    Matrix matrix=new Matrix(new int[][]{{},{},{}});
    System.out.println(matrix);
    matrix.determinant();
  }
  @Test
  public void testDeterminant(){
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,0}});
    assertTrue(matrix.determinant().compareTo(new BigDecimal(27))==0);
    matrix=new Matrix(new int[][]{{1,2},{3,4}});
    assertTrue(matrix.determinant().compareTo(new BigDecimal(-2))==0);
    matrix=new Matrix(new int[][]{{13}});
    assertTrue(matrix.determinant().compareTo(new BigDecimal(13))==0);
    matrix=new Matrix(new int[][]{{1,2,3,4},{8,7,6,5},{9,11,13,14},{1,3,2,7}});
    assertTrue(matrix.determinant().compareTo(new BigDecimal(27))==0);
  }

  @Test
  public void testT(){
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
    assertEquals(matrix.T(),new Matrix(new int[][]{{1,4,7},{2,5,8},{3,6,9}}));
  }

  @Test
  public void multi() throws UnmatchedMatrixSizeException {
    Matrix a=new Matrix(new int[][]{{1,2},{3,4},{5,6}});
    Matrix b=new Matrix(new int[][]{{1,0,1},{1,2,3}});
    Matrix c=a.multi(b);
    assertEquals(c,new Matrix(new int[][]{{3,4,7},{7,8,15},{11,12,23}}));
  }

  @Test(expected = UnmatchedMatrixSizeException.class)
  public void multi1() throws UnmatchedMatrixSizeException {
    Matrix a=new Matrix(new int[][]{{1,2,1},{3,4,0}});
    Matrix b=new Matrix(new int[][]{{1,0,1},{1,2,3}});
    Matrix c=a.multi(b);
  }


//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test(expected = UnmatchedMatrixSizeException.class)
  public void sub1() throws UnmatchedMatrixSizeException {
    Matrix.E.sub(new Matrix(new int[][]{{1,2},{3,4},{5,6}}));//这里会抛异常
  }
  @Test(expected = UnmatchedMatrixSizeException.class)
  public void sub2() throws UnmatchedMatrixSizeException {
    Matrix.E.sub(new Matrix(new int[][]{{1,2}}));//这里会抛异常
  }
  @Test(expected = UnmatchedMatrixSizeException.class)
  public void sub3() throws UnmatchedMatrixSizeException {
    new Matrix(new int[][]{{1,2,5}}).add(new Matrix(new int[][]{{1,2},{3,4},{5,6}}));//这里会抛异常
  }
//////////////////////////////////////////////////////////////////////////////////////////////////
  @Test()
  public void sub() throws UnmatchedMatrixSizeException {
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
    assertEquals(matrix.sub(Matrix.E).toString(), "<3*3 matrix>\n[0,\t2,\t3]\n[4,\t4,\t6]\n[7,\t8,\t8]");
    assertEquals(matrix.negate().negate().toString(), "<3*3 matrix>\n[1,\t2,\t3]\n[4,\t5,\t6]\n[7,\t8,\t9]");
    assertEquals(Matrix.E.sub(matrix).negate().toString(), "<3*3 matrix>\n[0,\t2,\t3]\n[4,\t4,\t6]\n[7,\t8,\t8]");
    assertEquals(matrix.sub(Matrix.E).sub(Matrix.E.multi("0.1")).add(Matrix.E.multi("1.1")).toStringHalfUp(0), "<3*3 matrix>\n[1,\t2,\t3]\n[4,\t5,\t6]\n[7,\t8,\t9]");

  }

  @Test
  public void equals() throws UnmatchedMatrixSizeException {
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
    assertEquals(matrix.negate().negate(), matrix);
    assertEquals(Matrix.E.sub(matrix).negate(), new Matrix(new double[][]{{0,2,3},{4,4,6},{7,8,8}}));
    assertEquals(matrix.sub(Matrix.E).sub(Matrix.E.multi("0.1")).add(Matrix.E.multi("1.1")), matrix);

    assertNotEquals(Matrix.E, new Matrix(new double[][]{{0, 1}, {1, 0}}));
    assertEquals(Matrix.E, new Matrix(new double[][]{{1, 0}, {0, 1}}));
    assertEquals(Matrix.E,new Matrix(new double[][]{{1}}));
    assertEquals(new Matrix(new double[][]{{1,0,0},{0,1,0},{0,0,1}}),Matrix.E);

    assertEquals(Matrix.E.sub(Matrix.E),Matrix.O);
    assertEquals(Matrix.O,Matrix.E.sub(Matrix.E));
    assertEquals(Matrix.E.sub(Matrix.E),new Matrix(new double[][]{{0, 0}, {0, 0}}));
    assertNotEquals(Matrix.E.sub(Matrix.E),new Matrix(new double[][]{{0, 1}, {1, 0}}));

    assertNotEquals(Matrix.E,new Matrix(new int[][]{{1,0,0},{0,1,0}}));

    assertNotEquals(new Matrix(new int[][]{{1,0,0},{0,1,0}}),new Matrix(new int[][]{{1,0,},{0,1,},{0,0}}));
    assertNotEquals(new Matrix(new int[][]{{1,0,0},{0,1,0},{0,0,1}}),new Matrix(new int[][]{{1,0,},{0,1,}}));

  }

  @Test
  public void testToString() throws UnmatchedMatrixSizeException {
    assertEquals("E", Matrix.E.toString());
    assertEquals("O", Matrix.O.toString());
    assertEquals("E", Matrix.E.multi("1").toString());
    assertEquals("O", Matrix.E.multi(0).toString());
    assertEquals("12E", Matrix.E.multi("12").toString());
    assertEquals("O", Matrix.E.multi("12").sub(Matrix.E.multi("12")).toString());
    Matrix matrix=new Matrix(new int[][]{{1,2,3},{4,5,6},{7,8,9}});
    assertEquals(matrix.toString(), "<3*3 matrix>\n[1,\t2,\t3]\n[4,\t5,\t6]\n[7,\t8,\t9]");
    assertNotEquals(Matrix.E.multi(1.2).toString(),"1.2E");
    assertEquals(Matrix.E.multi(1.2).toStringHalfUp(1),"1.2E");

    matrix=new Matrix(new double[][]{{1.1,2.2,3.3},{4.4,5.5,6.6},{7.7,8.8,9.9}});
    assertNotEquals(matrix.toString(), "<3*3 matrix>\n[1.1,\t2.2,\t3.3]\n[4.4,\t5.5,\t6.6]\n[7.7,\t8.8,\t9.9]");
    assertEquals(matrix.toStringHalfUp(1), "<3*3 matrix>\n[1.1,\t2.2,\t3.3]\n[4.4,\t5.5,\t6.6]\n[7.7,\t8.8,\t9.9]");
    //System.out.println(matrix.toStringHalfUp(1));
  }
}