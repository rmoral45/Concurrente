package Parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PetriNetConfiguratorTest {

    JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    PetriNetConfigurator pnConf1 = new PetriNetConfigurator(fr1.petriNet_test);
    @Test
    void getNumberPlacesTest() {
        Assertions.assertEquals(5, pnConf1.getNplaces());
    }

    JsonFileReader fr2 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    PetriNetConfigurator pnConf2 = new PetriNetConfigurator(fr2.petriNet_test);
    @Test
    void getNumberTransitionsTest() {
        Assertions.assertEquals(4, pnConf2.getNtransitions());
    }

    JsonFileReader fr3 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    PetriNetConfigurator pnConf3 = new PetriNetConfigurator(fr3.petriNet_test);
    @Test
    void getMarkingVectorTest() {
        Assertions.assertArrayEquals(new int[]{1, 0, 1, 1, 0}, pnConf3.getInitial_marking());
    }

    JsonFileReader fr4 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    PetriNetConfigurator pnConf4 = new PetriNetConfigurator(fr4.petriNet_test);
    @Test
    void getIncidenceMatrixTest() {
        Assertions.assertArrayEquals(new int[][]{{-1, 1, 0, 0}, {1, -1, 0, 0}, {-1, 1, -1, 1}, {0, 0, -1, 1}, {0, 0, 1, -1}}, pnConf4.getIncidence_matrix());
    }
}
