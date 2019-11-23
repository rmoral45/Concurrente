package Parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PetriNetConfiguratorTest {

    JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf1 = new PetriNetConfigurator(fr1.petriNet_test);
    @Test
    void getNumberPlacesTest() {
        Assertions.assertEquals(8, pnConf1.getNplaces());
    }

    JsonFileReader fr2 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf2 = new PetriNetConfigurator(fr2.petriNet_test);
    @Test
    void getNumberTransitionsTest() {
        Assertions.assertEquals(10, pnConf2.getNtransitions());
    }

    JsonFileReader fr3 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf3 = new PetriNetConfigurator(fr3.petriNet_test);
    @Test
    void getMarkingVectorTest() {
        Assertions.assertArrayEquals(new int[]{1, 2, 0, 0, 0, 0, 1, 0}, pnConf3.getInitial_marking());
    }

    JsonFileReader fr4 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf4 = new PetriNetConfigurator(fr4.petriNet_test);
    @Test
    void getAlpaVectorTest(){
        Assertions.assertArrayEquals(new long[]{0,0,0,0,0,0,0,0,0,0}, pnConf4.getAlpha_vector());
    }

    JsonFileReader fr5 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf5 = new PetriNetConfigurator(fr5.petriNet_test);
    @Test
    void getIncidenceMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{1, 0, 0, -1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 1, 0, -1, -1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, -1, 0, 0, 0},
                                                 {0, 0, 0, 0, 1, 0, 0, -1, 0, 0}, {0 ,0 ,0 ,0 ,0 ,1 ,0 , 0, -1, 0},
                                                 {0, 0, 0, -1, -1, -1, 1, 1, 1, 0}, {0, 1, 0, 0, 0, 0, 1, 1, 1, 0}}, pnConf5.getIncidence_matrix());
    }

    JsonFileReader fr6 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf6 = new PetriNetConfigurator(fr6.petriNet_test);
    @Test
    void getLectorArcsMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}, pnConf6.getLector_arcs());
    }
    JsonFileReader fr7 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
    PetriNetConfigurator pnConf7 = new PetriNetConfigurator(fr7.petriNet_test);
    @Test
    void getInhibArcsMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {1, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}, pnConf7.getInib_arcs());
    }
}
