package Monitor;
import Config.JsonFileReader;
import Config.ThreadTriggerConfigurator;
import Politica.Politica;
import ThreadLauncher.Launcher;
import org.junit.jupiter.api.Test;
import petriNet.PetriNet;

import java.io.IOException;

public class MonitorV2Test {


    @Test
    void ProductorConsumidorTemporizada() throws IOException, InterruptedException {
        JsonFileReader fr = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_final.json");

        //PetriNetConfigurator pnConf = new PetriNetConfigurator(fr.petriNet_test);
        PetriNet rdp = new PetriNet(fr.getPnConfigurator(), true,"/home/ramiro/repos/Concurrente/log_files/finalv10.log");
        Politica policy = new Politica(fr.getPolicyConfigurator());
        MonitorV2 monitor = new MonitorV2(rdp.getNtransitions(), policy,rdp);
        ThreadTriggerConfigurator ttConf = fr.getTriggerConfigurator();
        Launcher ln = new Launcher(ttConf, monitor);
        Thread.sleep(10000000);
    }
}
