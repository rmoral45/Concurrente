package petriNet;

import Parser.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PetriNetTest {


    JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    PetriNet rdp = new PetriNet(fr1.getPnConfigurator());

    @Test
    void getNumberPlacesTest() {
        Assertions.assertEquals(5, rdp.getNplaces());
    }

    @Test
    void getNumberTransitionsTest() {
        Assertions.assertEquals(4, rdp.getNtransitions());
    }

    @Test
    void getMarkingVectorTest() {
        Assertions.assertArrayEquals(new int[]{1, 0, 1, 1, 0}, rdp.getMark_vector());
    }

    @Test
    void getIncidenceMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{-1, 1, 0, 0}, {1, -1, 0, 0}, {-1, 1, -1, 1}, {0, 0, -1, 1}, {0, 0, 1, -1}}, rdp.getIncidenceMatrix());
    }

    @Test
    void probarDisparoTest() {
        Assertions.assertArrayEquals(new int[]{-1, 1, -1, 0, 0}, rdp.probarDisparo(0));
    }

    @Test
    void dispararTransicionTest() {
        Assertions.assertTrue(rdp.dispararTransicion(0));
    }

}