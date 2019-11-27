package Config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PetriNetConfiguratorTest {

    @Test
    void getNumberPlacesTest() {

        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertEquals(8, fr1.getPnConfigurator().getNplaces());
    }

    @Test
    void getNumberTransitionsTest() {
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertEquals(10, fr1.getPnConfigurator().getNtransitions());
    }

    @Test
    void getMarkingVectorTest() {
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertArrayEquals(new int[]{1, 2, 0, 0, 0, 0, 1, 0},fr1.getPnConfigurator().getInitial_marking());
    }

    @Test
    void getAlpaVectorTest(){
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertArrayEquals(new long[]{0,0,0,0,0,0,0,0,0,0}, fr1.getPnConfigurator().getAlpha_vector());
    }

    @Test
    void getIncidenceMatrixTest() {
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertArrayEquals(new int[][]{{1, 0, 0, -1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 1, 0, -1, -1, 0, 0, 0, 0}, {0, 0, 0, 1, 0, 0, -1, 0, 0, 0},
                                                 {0, 0, 0, 0, 1, 0, 0, -1, 0, 0}, {0 ,0 ,0 ,0 ,0 ,1 ,0 , 0, -1, 0},
                                                 {0, 0, 0, -1, -1, -1, 1, 1, 1, 0}, {0, 1, 0, 0, 0, 0, 1, 1, 1, 0}},
                                                 fr1.getPnConfigurator().getIncidence_matrix());
    }

    @Test
    void getLectorArcsMatrixTest() {
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                                                 fr1.getPnConfigurator().getLector_arcs());
    }

    @Test
    void getInhibArcsMatrixTest() {
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_paper.json");
        Assertions.assertArrayEquals(new int[][]{{0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                                                 {1, 0, 0, 0, 0, 0, 0, 0}, {1, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0},
                                                 {0, 0, 0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0, 0, 0}},
                                                 fr1.getPnConfigurator().getInib_arcs());
    }
}
