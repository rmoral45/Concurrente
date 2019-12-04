package Config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ThreadTriggerConfiguratorTest {


    @Test
    void NumberOfTriggersTest(){
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/prod_cons_temporizada.json");
        Assertions.assertEquals(9,fr1.getTriggerConfigurator().getThreadTriggers().size());
    }

    @Test
    void checkTriggerOneTest(){
        JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/prod_cons_temporizada.json");
        Assertions.assertArrayEquals(new int[]{0}, fr1.getTriggerConfigurator().getThreadTriggers().get(0).getSequence());
        Assertions.assertArrayEquals(new int[]{1}, fr1.getTriggerConfigurator().getThreadTriggers().get(1).getSequence());
        Assertions.assertEquals(10,fr1.getTriggerConfigurator().getThreadTriggers().get(1).getNshoots());
        Assertions.assertEquals(10,fr1.getTriggerConfigurator().getThreadTriggers().get(0).getNshoots());
    }

}
