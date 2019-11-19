package ThreadLauncher;
import java.util.ArrayList;
import Monitor.MonitorV2;
import ThreadLauncher.Disparador;

public class Launcher {
    /*
       Este constructor implementa un metodo de lanzado trivial, recibe la cantidad
       de hilos deseada y los lanza asignandole a cada uno una sola transcicion a disparar
    */

    public Launcher(int nthreads, MonitorV2 monitor){

        ArrayList<Thread> threadPool = new ArrayList<Thread>();

        for (int i=0; i< nthreads; i++)
            threadPool.add(new Thread( new Disparador(new int[] {i},i,monitor) ));

        for (int i=0; i< nthreads; i++)
            threadPool.get(i).start();

        

    }
}
