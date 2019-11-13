package Monitor;
import Colas.ColaCondicion;
import Politica.PoliticMode;
import Politica.Politica;
import petriNet.MathOperator;
import petriNet.PetriNet;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorV2 {

    //FIXME Utilizar ReentrantLock En lugar de Lock para tener acceso a todos los metodos
    private final Lock conditionQueueLock = new ReentrantLock(); // lock para generar colas
    private final ArrayList<ColaCondicion> colasCondicion = new ArrayList<ColaCondicion>();
    private final Semaphore ingressSemaphore = new Semaphore(1,true);
    private Politica policy;
    private PetriNet petriNet;
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
            if (petriNet.dispararTransicion(numTranscicion)){
                //Obtener sensibilizadas
                //Ver si hay alguna en la cola esperando por una transcicon
                //que fue sensibilizada luego del disparo
                //si hay alguna adentro que pueda disparar le preg a la politica cual
                //lo despierto y me voy
                //sino libero el semaforo de entrada y me voy

            }
            else{
                ingressSemaphore.release();
                colasCondicion.get(numTranscicion).encolar();
                //Si me despertaron se que puedo disparar
                //Entonces disparo
                //Ver si hay alguna en la cola esperando por una transcicon
                //que fue sensibilizada luego del disparo
                //si puedo despertar alguno de adentro le preg cual a la politica
                //des pierto al hilo y me voy
                //sino libero el Semaforo de entrdaa al monitor


            }


        }
        catch(InterruptedException e){
            System.exit(1);
        }
    }

    /*
     * Opera para saber sobre que colas se pueden despertar hilos,
     * es decir, que haya alguien esperando para disparar X transcicion y dicha
     * transcicion este sensibilizada
     */
    private int[] getReadyVect(int [] sensibilizadas){
        MathOperator mo = new MathOperator();
        return mo.andVector(sensibilizadas.length, getWaitingVect(), sensibilizadas);
    }

    /*
     *@brief : Recorre las cosas de condicion para ver si hay hilos esperando
     */
    private int[] getWaitingVect(){

        int[] wv = new int[colasCondicion.size()];
        for (int i = 0; i < colasCondicion.size(); i++){
            if (colasCondicion.get(i).getQueueLen() > 0)
                wv[i] = 1;
        }
        return wv;
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
