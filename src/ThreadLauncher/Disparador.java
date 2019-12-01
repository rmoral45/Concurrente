package ThreadLauncher;
import Config.ThreadTrigger;
import Monitor.MonitorV2;
import java.util.Arrays;
import java.util.List;

public class Disparador implements Runnable {

    private List<Integer> transitionSequence;
    private int nextFire = 0;
    private MonitorV2 monitor;
    private int [] sequenciaDisparos;
    private int ntimes;

    public Disparador(ThreadTrigger tt, MonitorV2 m) {

        sequenciaDisparos = tt.getSequence();
        ntimes = tt.getNshoots();
        this.monitor = m;
    }

    @Override
    public void run() {
       for(int i = 0; i < ntimes; i++)
           for (int f : sequenciaDisparos) {
               try {
                   monitor.dispararTemp(f);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }

    }
}
