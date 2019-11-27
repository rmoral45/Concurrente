package Config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class PetriNetConfiguratorTest {

    //---------------FIRST TEST--------------------------
    JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf1;
    {
        try {
            pnConf1 = new PetriNetConfigurator(fr1.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    void getNumberPlacesTest() {
        Assertions.assertEquals(8, pnConf1.getNplaces());
    }

    //---------------SECOND TEST--------------------------
    JsonFileReader fr2 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf2;

    {
        try {
            pnConf2 = new PetriNetConfigurator(fr2.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getNumberTransitionsTest() {
        Assertions.assertEquals(10, pnConf2.getNtransitions());
    }

    //---------------THIRD TEST--------------------------
    JsonFileReader fr3 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf3;

    {
        try {
            pnConf3 = new PetriNetConfigurator(fr3.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getMarkingVectorTest() {
        Assertions.assertArrayEquals(new int[]{1, 2, 0, 0, 0, 0, 1, 0}, pnConf3.getInitial_marking());
    }

    //---------------FOURTH TEST--------------------------
    JsonFileReader fr4 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf4;

    {
        try {
            pnConf4 = new PetriNetConfigurator(fr4.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAlpaVectorTest(){
        Assertions.assertArrayEquals(new long[]{0,0,0,0,0,0,0,0,0,0}, pnConf4.getAlpha_vector());
    }

    //---------------FIFTH TEST--------------------------
    JsonFileReader fr5 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf5;

    {
        try {
            pnConf5 = new PetriNetConfigurator(fr5.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getIncidenceMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{1, 0, 0, -1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 1, 0, -1, -1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, -1, 0, 0, 0},
                                                 {0, 0, 0, 0, 1, 0, 0, -1, 0, 0}, {0 ,0 ,0 ,0 ,0 ,1 ,0 , 0, -1, 0},
                                                 {0, 0, 0, -1, -1, -1, 1, 1, 1, 0}, {0, 1, 0, 0, 0, 0, 1, 1, 1, 0}}, pnConf5.getIncidence_matrix());
    }

    //---------------SIXTH TEST--------------------------
    JsonFileReader fr6 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf6;

    {
        try {
            pnConf6 = new PetriNetConfigurator(fr6.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getLectorArcsMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}, pnConf6.getLector_arcs());
    }

    //---------------SEVENTH TEST--------------------------
    JsonFileReader fr7 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf7;

    {
        try {
            pnConf7 = new PetriNetConfigurator(fr7.petriNet_test);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getInhibArcsMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}, pnConf7.getInib_arcs());
    }
}
