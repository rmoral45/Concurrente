package ThreadLauncher;
import java.util.ArrayList;

import Config.ThreadTrigger;
import Config.ThreadTriggerConfigurator;
import Monitor.MonitorV2;


public class Launcher {

    private ArrayList<Thread> threadPool;

    public Launcher(ThreadTriggerConfigurator tcfg, MonitorV2 monitor){

        threadPool = new ArrayList<Thread>();
        ArrayList<ThreadTrigger> tt = tcfg.getThreadTriggers();
        for (int i=0; i < tt.size() ; i++)
            threadPool.add(new Thread( new Disparador(tt.get(i), monitor) ));

        for (int i = 0; i < tt.size(); i++)
            threadPool.get(i).start();

        

    }
}
