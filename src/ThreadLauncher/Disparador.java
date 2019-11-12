import java.util.Arrays;
import java.util.List;

public class Disparador implements Runnable {

    private List<Integer> transitionSequence;
    private int nextFire = 0;
    private MonitorV2 monitor;
    private int disparoDeseado;

    public Disparador(int d, MonitorV2 m) {

        this.disparoDeseado = d;
        this.monitor = m;
    }

    @Override
    public void run() {
        while (true) {

            try {
                this.monitor.disparar(this.disparoDeseado);
            } catch (InterruptedException e) {
                System.out.print("Agarre la excepcion en mi propio metodo");
            }
        }

    }
}
