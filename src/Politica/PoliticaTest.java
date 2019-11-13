package Politica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import petriNet.MathOperator;

import java.security.InvalidAlgorithmParameterException;


class PoliticaTest {

    @Test
    void invalidParamTest(){
        Politica pol = new Politica(7, PoliticMode.ROND_ROBIN);
        assertThrows(InvalidAlgorithmParameterException.class,() ->{
            pol.getRoundRobinNext(new int[7]);});
    }

    /*
     * Testea el caso donde hay elementos maximos iguales,usa solo le objeto Math Operator
     */
    @Test
    void indexMaxVectTest(){

        assertArrayEquals(new int[]{2,6}           , MathOperator.getMaxIndexVect(new int[]{1,1,3,1,1,2,3}));

        assertArrayEquals(new int[]{0,1,2,3,4,5,6} , MathOperator.getMaxIndexVect(new int []{1,1,1,1,1,1,1}));

        assertArrayEquals(new int[]{3}             , MathOperator.getMaxIndexVect(new int []{1,1,1,7,1,1,1}));

        assertArrayEquals(new int[]{0}             , MathOperator.getMaxIndexVect(new int []{7,6,5,4,3,2,1}));

        assertArrayEquals(new int[]{7}             , MathOperator.getMaxIndexVect(new int []{1,1,1,7,1,1,1,9}));
    }

}