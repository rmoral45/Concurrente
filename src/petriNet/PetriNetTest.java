package petriNet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


public class PetriNetTest {

    PetriNet rdp = new PetriNet("/home/ramiro/repos/Concurrente/src/petriNet/petriNet_parameters.json");


    @Test
    void getNumberPlacesTest(){
        Assertions.assertEquals(5, rdp.getNplaces());
    }

    @Test
    void getNumberTransitionsTest(){
        Assertions.assertEquals(4, rdp.getNtransitions());
    }

    @Test
    void getMarkingVectorTest(){
        Assertions.assertArrayEquals(new int[] {1,0,1,1,0}, rdp.getMark_vector());
    }

    @Test
    void getIncidenceMatrixTest(){
        Assertions.assertArrayEquals(new int[][]{{-1,1,0,0},{1,-1,0,0},{-1,1,-1,1},{0,0,-1,1},{0,0,1,-1}}, rdp.getIncidenceMatrix());
    }

    @Test
    void probarDisparoTest(){
        Assertions.assertArrayEquals(new int[]{-1,1,-1,0,0}, rdp.probarDisparo(0));
    }

    @Test
    void dispararTransicionTest(){
        Assertions.assertTrue(rdp.dispararTransicion(0));
    }
}
