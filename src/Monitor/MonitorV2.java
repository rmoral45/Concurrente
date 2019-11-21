package Monitor;
import Colas.ColaCondicion;
import Politica.PoliticMode;
import Politica.Politica;
import petriNet.PetriNet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorV2 {

    private final ReentrantLock conditionQueueLock = new ReentrantLock(); // lock para generar colas
    private final ArrayList<ColaCondicion> colasCondicion = new ArrayList<>();
    private final Semaphore ingressSemaphore = new Semaphore(1,true);
    private Politica policy;
    private PetriNet petriNet;
    private  boolean K;

    public MonitorV2(int ntrans,PoliticMode polMode, PetriNet pn){

        policy = new Politica( new int [] {1,1,1,1,1}, polMode);
        petriNet = pn;
        K = false;
        for (int i=0; i<ntrans; i++) {
            ColaCondicion cc = new ColaCondicion(conditionQueueLock.newCondition(),i);
            colasCondicion.add(cc);
        }

    }

    /*
     * Intenta obtener el recurso de la RdP
     */
    public void disparar(int numTranscicion){

        try{
            ingressSemaphore.acquire();
            conditionQueueLock.lock();
            if (petriNet.dispararTransicion(numTranscicion)){
                afterFireAction();
            }
            else{
                ingressSemaphore.release();
                colasCondicion.get(numTranscicion).encolar();
                //Si me despertaron se que puedo disparar
                petriNet.dispararTransicion(numTranscicion);
                afterFireAction();
            }
        }
        catch(InterruptedException e){
            System.exit(1);
        }
    }
    /*
        Metodo que aplica la ec de estado generalizada y lo hace adentro de un loop como
        plantea mico en el diagrama de secuencia
     */
    public void dispararV2(int numTranscicion) throws InterruptedException {

            ingressSemaphore.acquire();
            conditionQueueLock.lock();
            K = true;
            while(K){

                K =petriNet.dispararTransicion(numTranscicion);
                if (K){
                    int [] sensibilizadas;
                    sensibilizadas = petriNet.obtenerSensibilizadas(getWaitingVect());
                    if (Arrays.equals(new int[sensibilizadas.length],sensibilizadas)){
                        K= false;
                    }
                    else{
                        int nextAwake = policy.getNextAwake(sensibilizadas);
                        colasCondicion.get(nextAwake).desencolar();
                        conditionQueueLock.unlock();
                        return;
                    }
                }
                else{
                    ingressSemaphore.release();
                    colasCondicion.get(numTranscicion).encolar();
                }

            }

            ingressSemaphore.release();
    }
    /*
     * Debe ser ejecutada por todos los hilos luego de realizar un disparo valido
     * Verifica si puede despertar algun hilo, en caso de poder lo hace
     * Encaso de no poder despertar libera lel semaforo de entrada al monitor
     */
    private void afterFireAction(){
        int [] sensibilizadas;
        sensibilizadas = petriNet.obtenerSensibilizadas(getWaitingVect());

        if (Arrays.equals(new int[sensibilizadas.length],sensibilizadas)){
            conditionQueueLock.unlock();
            ingressSemaphore.release();
        }
        else{
            int nextAwake = policy.getNextAwake(sensibilizadas);
            colasCondicion.get(nextAwake).desencolar();
            conditionQueueLock.unlock();
        }
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
    /*
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

    }*/
}
