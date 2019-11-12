import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorV2 {

    private final Lock conditionQueueLock = new ReentrantLock(); // lock para generar colas
    private final ArrayList<ColaCondicion> colasCondicion = new ArrayList<ColaCondicion>();
    private final Semaphore ingressSemaphore = new Semaphore(1,true);
    private Politica policy;
    private final int[] fake_sens= {1,1,0,0,0,1};

    public MonitorV2(int nthreads, PoliticMode polMode){

        policy = new Politica(nthreads, polMode);
        for (int i=0; i<nthreads; i++) {
            ColaCondicion cc = new ColaCondicion(conditionQueueLock.newCondition(),i);
            colasCondicion.add(cc);
        }

    }

    /*
     * @brief  : intenta obtener el recurso de la RdP
     * @return : true si se pudo realizar, false si la transcicion no esta sensibilizada
     */
    public void disparar(int numTranscicion){

        try{
            ingressSemaphore.acquire();
            conditionQueueLock.lock();
            if (petriNet.disparar(numTranscicion) == true){

            }
            else{
                ingressSemaphore.release();
                colasCondicion.get()
            }


        }
        catch(InterruptedException e){
            System.exit(1);
        }
    }
    /*
        Para probar boludeses con las colas etc
     */
    public void  dispararFake(int t) throws InterruptedException {
        ingressSemaphore.acquire();
        conditionQueueLock.lock();
        try {
            if (t == 10) {
                //System.out.print("Hilo despertador entrando al monitor\n");
                for (int i = 0; i < colasCondicion.size(); i++) {
                    int awake = policy.getNextAwake(fake_sens);
                    if (colasCondicion.get(awake).getQueueLen() > 0)
                        colasCondicion.get(awake).desencolar();
                }
                ingressSemaphore.release();
            } else {
                    ingressSemaphore.release();
                    //System.out.print("Hilo numero " + t + " yendoseeeee a dormir\n");
                    colasCondicion.get(t).encolar();
            }
        }
        finally{
            conditionQueueLock.unlock();
        }

    }
}
