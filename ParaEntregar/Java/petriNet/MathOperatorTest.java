package petriNet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.security.InvalidAlgorithmParameterException;

class MathOperatorTest {

    MathOperator mathOperator = new MathOperator();

    @Test
    void AddVectorTest() throws InvalidAlgorithmParameterException {
        //------------------addVector assertions------------------
        Assertions.assertArrayEquals(new int[]{10,2}, mathOperator.addVector(new int[]{7,1},new int[]{3,1}));
        //Assertions.assertArrayEquals(new int[]{10,2}, MathOperator.addVector(new int[]{7,1},new int[]{3,1}));
    }

    //VERIFICAR ESTO!!
    @Test
    void AndVectorTest() throws InvalidAlgorithmParameterException {
        //------------------ProdVector assertions------------------
        Assertions.assertArrayEquals(new int[]{1,1,1,5}, mathOperator.andVector(new int[]{1,1,1,5}, new int[]{1,1,1,5}));
    }

    @Test
    void InnerProdTest() throws InvalidAlgorithmParameterException{
        //------------------innerProdVector assertions------------------
        Assertions.assertArrayEquals(new int[]{4,4}, mathOperator.innerProdVector(new int[]{2,2},new int[]{2,2}));
        //Assertions.assertArrayEquals(new int[]{6,4}, mathOperator.innerProdVector(new int[]{1,2},new int[]{2,2}));
    }

    @Test
    void MatrixAddTest() throws InvalidAlgorithmParameterException{
        Assertions.assertArrayEquals(new int[][]{{1,1,4},{2,3,0},{0,0,0}}, mathOperator.matrixAdd(new int[][]{{1,0,3},{1,2,0},{0,0,0}}, new int[][]{{0,1,1},{1,1,0},{0,0,0}}));
    }

    @Test
    void MatrixProdTest() throws InvalidAlgorithmParameterException{
        Assertions.assertArrayEquals(new int[][]{{2,4},{6,8}}, mathOperator.matrixProd(new int[][]{{1,2},{3,4}}, new int[][]{{2,2},{2,2}}));
        //Assertions.assertArrayEquals(new int[][]{{2,3},{6,10}}, mathOperator.matrixProd(new int[][]{{1,2},{3,4}}, new int[][]{{2,2},{2,2}}));
    }

    @Test
    void VectMatrixProdTest() throws InvalidAlgorithmParameterException{
        Assertions.assertArrayEquals(new int[]{22,24}, mathOperator.vectmatProd(new int[][]{{5,4},{3,6}}, new int[]{2,3}));
    }

    @Test
    void GetMaxIndexTest(){
        Assertions.assertEquals(9, mathOperator.getMaxIndex(new int[]{1,2,3,2,7,6,7,8,9,10}));
        //Assertions.assertEquals(9, mathOperator.getMaxIndex(new int[]{1,2,55,2,7,6,7,8,9,10}));
    }

    @Test
    void GetMinIndexTest(){
        Assertions.assertEquals(2, mathOperator.getMinIndex(new int[]{10,25,0,3,1,6,3,6}));
        //Assertions.assertEquals(5, mathOperator.getMinIndex(new int[]{10,25,0,3,1,6,3,6}));
    }

    @Test
    void GetMaxValueTest(){
        Assertions.assertEquals(10, mathOperator.getMaxValue(new int[]{1,1,4,6,8,0,1,10,2,9}));
        //Assertions.assertEquals(10, mathOperator.getMaxValue(new int[]{1,1,4,6,8,0,1,10,2,9,25}));
    }

    @Test
    void GetMaxIndexVectorTest(){
        Assertions.assertArrayEquals(new int[]{2,10}, mathOperator.getMaxIndexVect(new int[]{0,1,15,0,14,3,8,9,1,11,15}));
        //Assertions.assertArrayEquals(new int[]{2,10}, mathOperator.getMaxIndexVect(new int[]{0,1,15,0,14,3,8,9,1,11,16}));
    }

    @Test
    void GetMinValueTest(){
        Assertions.assertEquals(0, mathOperator.getMinValue(new int[]{0,44,22,10,5,7,22,30,1,7}));
        Assertions.assertEquals(1, mathOperator.getMinValue(new int[]{0,44,22,10,5,7,22,30,1,7}));
    }


}
