package Monitor;
import static org.junit.jupiter.api.Assertions.*;

import Config.JsonFileReader;
import Config.PetriNetConfigurator;
import Config.ThreadTrigger;
import Config.ThreadTriggerConfigurator;
import Politica.PoliticMode;
import ThreadLauncher.Disparador;
import ThreadLauncher.Launcher;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import petriNet.MathOperator;
import petriNet.PetriNet;

import java.io.IOException;
import java.util.ArrayList;

public class MonitorV2Test {


    @Test
    void ProductorConsumidorTemporizada() throws IOException, InterruptedException {
        JsonFileReader fr = new JsonFileReader("/home/dabratte/repos/Concurrente/src/petriNet/red_final.json");

        //PetriNetConfigurator pnConf = new PetriNetConfigurator(fr.petriNet_test);
        PetriNet rdp = new PetriNet(fr.getPnConfigurator(), true,"/home/dabratte/repos/Concurrente/log_files/finalv9.log");
        MonitorV2 monitor = new MonitorV2(rdp.getNtransitions(),PoliticMode.HIGH_PRIO,rdp);
        ThreadTriggerConfigurator ttConf = fr.getTriggerConfigurator();
        Launcher ln = new Launcher(ttConf, monitor);
        Thread.sleep(10000000);
    }
}
