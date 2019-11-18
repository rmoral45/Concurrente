package Politica;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import petriNet.MathOperator;

import java.security.InvalidAlgorithmParameterException;


class PoliticaTest {

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
/*
    @Test
    void getNextwithHighPrioTest(){
        int nq = 7;
        Politica pol3 = new Politica(nq, PoliticMode.HIGH_PRIO);
        assertEquals(3,pol3.getNextAwake(new int[] {1,1,1,1}));
    }*/
    @Test
    void getNextWithRandomtTest(){

        Politica pol1 = new Politica(new int [] {0,1,2,3,4,5}, PoliticMode.RANDOM);

        assertEquals(0,pol1.getNextAwake(new int[] {1,0}));

        assertEquals(1,pol1.getNextAwake(new int[] {0,1}));

        assertEquals(0,pol1.getNextAwake(new int[] {1}));

        assertEquals(3,pol1.getNextAwake(new int[] {0,0,0,1}));
    }

    @Test
    void getNextWithHighPrioTest(){
        int nq2 = 7;
        Politica pol2 = new Politica(new int [] {2, 1, 1, 3, 5}, PoliticMode.HIGH_PRIO);

        assertEquals(4,pol2.getNextAwake(new int[] {1,1,1,1,1}));

        assertEquals(3,pol2.getNextAwake(new int[] {1,1,1,1,0}));

        assertEquals(1,pol2.getNextAwake(new int[] {0,1,0,0,0}));

        assertEquals(4,pol2.getNextAwake(new int[] {0,0,0,1,1}));

        assertEquals(0,pol2.getNextAwake(new int[] {1,0,1,0,0}));

        assertEquals(3,pol2.getNextAwake(new int[] {1,0,0,1,0}));

        assertEquals(1,pol2.getNextAwake(new int[] {0,1,0,0,0}));

        assertEquals(2,pol2.getNextAwake(new int[] {0,0,1,0,0}));
    }

}