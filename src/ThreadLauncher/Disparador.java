package ThreadLauncher;
import Monitor.MonitorV2;
import java.util.Arrays;
import java.util.List;

public class Disparador implements Runnable {

    private List<Integer> transitionSequence;
    private int nextFire = 0;
    private MonitorV2 monitor;
    private int [] sequenciaDisparos;
    private int ntimes;

    public Disparador(int [] seq,int nt, MonitorV2 m) {

        sequenciaDisparos= seq;
        ntimes = nt;
        this.monitor = m;
    }

    @Override
    public void run() {
       for(int i=0; i<ntimes;i++)
           for (int f : sequenciaDisparos)
               monitor.disparar(f);

    }
}
