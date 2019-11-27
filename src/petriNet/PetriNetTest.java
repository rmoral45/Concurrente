package petriNet;

import Config.JsonFileReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class PetriNetTest {


    //JsonFileReader fr1 = new JsonFileReader("/home/dabratte/repos/Concurrente/src/petriNet/red_paper.json");
/*
    @Test
    void getNumberPlacesTest() {
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator());
        Assertions.assertEquals(5, rdp.getNplaces());
    }

    @Test
    void getNumberTransitionsTest() {
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator());
        Assertions.assertEquals(4, rdp.getNtransitions());
    }

    @Test
    void getMarkingVectorTest() {
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator());
        Assertions.assertArrayEquals(new int[]{1, 0, 1, 1, 0}, rdp.getMark_vector());
    }

    @Test
    void getIncidenceMatrixTest() {
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator());
        Assertions.assertArrayEquals(new int[][]{{-1, 1, 0, 0}, {1, -1, 0, 0}, {-1, 1, -1, 1}, {0, 0, -1, 1}, {0, 0, 1, -1}}, rdp.getIncidenceMatrix());
    }

    @Test
    void probarDisparoTest() throws InvalidAlgorithmParameterException {
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator());
        Assertions.assertArrayEquals(new int[]{-1, 1, -1, 0, 0}, rdp.probarDisparo(0));
    }

    @Test
    void dispararTransicionTest() throws InvalidAlgorithmParameterException {
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator());
        Assertions.assertTrue(rdp.dispararTransicion(0));
    }*/

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    //                    Tests para Ecuacion de Estado Extendida

    /////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    void validateT0Shot() throws Exception {
        JsonFileReader fr1 = new JsonFileReader("/home/dabratte/repos/Concurrente/src/petriNet/red_paper.json");
        long currT = 1000; //seteo tiempo cuyalquyiera,total ninguna es temporal
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator(),true,"/home/dabratte/repos/Concurrente/log_files/test.log");
        Assertions.assertEquals(FireResultType.SUCCESS,rdp.dispararExtendida(0, currT));
    }
    @Test
    void validateT3Shot() throws Exception {
        JsonFileReader fr1 = new JsonFileReader("/home/dabratte/repos/Concurrente/src/petriNet/red_paper.json");
        long currT = 1000; //seteo tiempo cuyalquyiera,total ninguna es temporal
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator(), true,"/home/dabratte/repos/Concurrente/log_files/test.log");
        Assertions.assertEquals(FireResultType.SUCCESS,rdp.dispararExtendida(3, currT));
    }
    @Test
    void validateT4Shot() throws Exception {
        JsonFileReader fr1 = new JsonFileReader("/home/dabratte/repos/Concurrente/src/petriNet/red_paper.json");
        long currT = 1000; //seteo tiempo cuyalquyiera,total ninguna es temporal
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator(),true,"/home/dabratte/repos/Concurrente/log_files/test.log");
        Assertions.assertEquals(FireResultType.RESOURCE_UNAVAILABLE,rdp.dispararExtendida(4, currT));
    }
    @Test
    void validateT5Shot() throws Exception {
        JsonFileReader fr1 = new JsonFileReader("/home/dabratte/repos/Concurrente/src/petriNet/red_paper.json");
        long currT = 1000; //seteo tiempo cuyalquyiera,total ninguna es temporal
        PetriNet rdp = new PetriNet(fr1.getPnConfigurator(),true,"/home/dabratte/repos/Concurrente/log_files/test.log");
        Assertions.assertEquals(FireResultType.RESOURCE_UNAVAILABLE,rdp.dispararExtendida(5, currT));
    }
}