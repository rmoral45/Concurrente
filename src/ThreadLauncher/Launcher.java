package ThreadLauncher;
import java.util.ArrayList;

import Config.ThreadTrigger;
import Config.ThreadTriggerConfigurator;
import Monitor.MonitorV2;
import ThreadLauncher.Disparador;

public class Launcher {
    /*
       Este constructor implementa un metodo de lanzado trivial, recibe la cantidad
       de hilos deseada y los lanza asignandole a cada uno una sola transcicion a disparar
    */
    ArrayList<Thread> threadPool;

    public Launcher(ThreadTriggerConfigurator tcfg, MonitorV2 monitor){

        threadPool = new ArrayList<Thread>();
        ArrayList<ThreadTrigger> tt = tcfg.getThreadTriggers();
        for (int i=0; i < tt.size() ; i++)
            threadPool.add(new Thread( new Disparador(tt.get(i), monitor) ));

        for (int i = 0; i < tt.size(); i++)
            threadPool.get(i).start();

        

    }
}
