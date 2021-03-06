import Config.JsonFileReader;
import Config.ThreadTriggerConfigurator;
import Monitor.MonitorV2;
import Politica.Politica;
import ThreadLauncher.Launcher;
import petriNet.PetriNet;

import java.io.IOException;

public class main {

    public static void main(String[] args) throws IOException, InterruptedException {
        -
        //JsonFileReader fr = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_final_prio_bajada.json");
        JsonFileReader fr = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_final_prio_subida.json");
        //JsonFileReader fr = new JsonFileReader("/home/ramiro/repos/Concurrente/src/petriNet/red_final.json");
        //PetriNet rdp = new PetriNet(fr.getPnConfigurator(), true,"/home/ramiro/repos/Concurrente/log_files/finalTest2_prioBajada.log");
        PetriNet rdp = new PetriNet(fr.getPnConfigurator(), true,"/home/ramiro/repos/Concurrente/log_files/finalTest2_prioSubida.log");
        //PetriNet rdp = new PetriNet(fr.getPnConfigurator(), true,"/home/ramiro/repos/Concurrente/log_files/finalTest2.log");


        Politica policy = new Politica(fr.getPolicyConfigurator());
        MonitorV2 monitor = new MonitorV2(rdp.getNtransitions(), policy,rdp);
        ThreadTriggerConfigurator ttConf = fr.getTriggerConfigurator();
        Launcher ln = new Launcher(ttConf, monitor);
        Thread.sleep(10000000);

    }
}

