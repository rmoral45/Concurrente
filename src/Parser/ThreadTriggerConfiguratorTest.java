package Parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ThreadTriggerConfiguratorTest {

    JsonFileReader fr1 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    ThreadTriggerConfigurator ttConf1 = new ThreadTriggerConfigurator(fr1.Triggers_test);
    @Test
    void NumberOfTriggersTest(){
        Assertions.assertEquals(2,ttConf1.getThreadTriggers().size());
    }

    JsonFileReader fr2 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    ThreadTriggerConfigurator ttConf2 = new ThreadTriggerConfigurator(fr2.Triggers_test);
    ArrayList<ThreadTrigger> ALtt1 = ttConf2.getThreadTriggers();
    @Test
    void checkTriggerOneTest(){
        Assertions.assertArrayEquals(new int[]{3,4,5}, ALtt1.get(1).getSequence());
        Assertions.assertArrayEquals(new int[]{0,1,2}, ALtt1.get(0).getSequence());
        Assertions.assertEquals(2,ALtt1.get(0).getNshoots());
    }

    JsonFileReader fr3 = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/parameters.json");
    ThreadTriggerConfigurator ttConf3 = new ThreadTriggerConfigurator(fr3.Triggers_test);
    ArrayList<ThreadTrigger> ALtt2 = ttConf3.getThreadTriggers();
    @Test
    void checkTriggerTwoTest(){
        Assertions.assertArrayEquals(new int[]{0,1,2}, ALtt2.get(0).getSequence());
        Assertions.assertEquals(2,ALtt1.get(1).getNshoots());
    }

}
