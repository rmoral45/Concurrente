package Monitor;
import Colas.ColaCondicion;
import MyLogger.MyLoggerWrapper;
import Politica.PoliticMode;
import Politica.Politica;
import petriNet.FireResultType;
import petriNet.MathOperator;
import petriNet.PetriNet;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

public class MonitorV2 {

    //private final ReentrantLock conditionQueueLock = new ReentrantLock(); // lock para generar colas
    private final ArrayList<ColaCondicion> colasCondicion = new ArrayList<>();
    private final Semaphore ingressSemaphore  = new Semaphore(1,true);
    private final Semaphore shotSemaphore = new Semaphore(1,true);
    private Politica policy;
    private PetriNet petriNet;
    private  boolean K;
    MyLoggerWrapper logger;

    public MonitorV2(int ntrans,PoliticMode polMode, PetriNet pn) throws IOException {

        policy = new Politica( new int [] {1,1,1,1,1}, polMode);
        petriNet = pn;
        K = false;
        logger = MyLoggerWrapper.getInstance("/home/dabratte/repos/Concurrente/log_files/pctemp.log");
        for (int i = 0; i < ntrans; i++) {
            ColaCondicion cc = new ColaCondicion(i);
            colasCondicion.add(cc);
        }

    }

    /*
     * Intenta obtener el recurso de la RdP
     */
    public void disparar(int numTranscicion){

        try{
            ingressSemaphore.acquire();
            //conditionQueueLock.lock();
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
        catch(InterruptedException | InvalidAlgorithmParameterException e){
            System.exit(1);
        }
    }
    /*
        Metodo que aplica la ec de estado generalizada y lo hace adentro de un loop como
        plantea mico en el diagrama de secuencia
     */
    public void dispararV2(int numTranscicion) throws InterruptedException, InvalidAlgorithmParameterException {

            ingressSemaphore.acquire();
            //conditionQueueLock.lock();
            K = true;
            while(K){

                K = petriNet.dispararTransicion(numTranscicion);
                if (K){
                    int [] sensibilizadas;
                    sensibilizadas = petriNet.obtenerSensibilizadas(getWaitingVect());
                    if (Arrays.equals(new int[sensibilizadas.length],sensibilizadas)){
                        K= false;
                    }
                    else{
                        int nextAwake = policy.getNextAwake(sensibilizadas);
                        colasCondicion.get(nextAwake).desencolar();
                        //conditionQueueLock.unlock();
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

    public void dispararTemp(int numTranscicion) throws Exception {

        ingressSemaphore.acquire();
        
        K = true;
        FireResultType fr;
        while(K){
            shotSemaphore.acquire();  //Semaforo que toman los hilos temporales
            long currentTime = System.currentTimeMillis();
            fr = petriNet.dispararExtendida(numTranscicion, currentTime);

            if (fr == FireResultType.SUCCESS){
                int [] sensibilizadas;
                sensibilizadas = petriNet.obtenerSensibilizadaExtendida(currentTime);
                /*despierto a todos los hilos que deben setear su propio tiempo*/
                //FIXME se debe havcer una and con los hilos que estan esperando en las colas primero
                sensibilizadas = awakeTemporal(sensibilizadas);
                wakeUp(sensibilizadas);
                return;
            }

            else if (fr == FireResultType.RESOURCE_UNAVAILABLE){
                wakeUp(new int [colasCondicion.size()]);
                colasCondicion.get(numTranscicion).encolar();
                if (petriNet.getAlpha(numTranscicion) > 0) // fui despertado para setear tiempo
                {
                    currentTime = System.currentTimeMillis();
                    colasCondicion.get(numTranscicion).encolarTemporal(petriNet.getRemainingTime(currentTime, numTranscicion));
                }
            }
            
            /*Si el resultado no fue exitoso por falta de tiempo*/
            else if ((fr == FireResultType.TIME_DISABLED)) {
            //    if (logger != null) {
           //         logger.myLogger.info("\nDisparo de T" + numTranscicion + "FALLO POR TIEMPO\n");
             //   }
                wakeUp(new int [colasCondicion.size()]);
                colasCondicion.get(numTranscicion).encolarTemporal(petriNet.getRemainingTime(currentTime,numTranscicion));
            }

        }

    }

    /**
     * Debe ser ejecutada por todos los hilos luego de realizar un disparo valido
     * Verifica si puede despertar algun hilo, en caso de poder lo hace
     * Encaso de no poder despertar libera lel semaforo de entrada al monitor
     */
    private void afterFireAction() throws InvalidAlgorithmParameterException {
        int [] sensibilizadas;
        sensibilizadas = petriNet.obtenerSensibilizadas(getWaitingVect());

        if (Arrays.equals(new int[sensibilizadas.length],sensibilizadas)){
            //conditionQueueLock.unlock();
            ingressSemaphore.release();
        }
        else{
            int nextAwake = policy.getNextAwake(sensibilizadas);
            colasCondicion.get(nextAwake).desencolar();
            //conditionQueueLock.unlock();
        }
    }

    private void wakeUp(int [] sensibilizadas) throws InvalidAlgorithmParameterException {

        int [] wv = getWaitingVect();
        sensibilizadas = MathOperator.innerProdVector(wv,sensibilizadas);

        if (shotSemaphore.hasQueuedThreads()){
            /*
                Le cedo el monitor al hilo que fue despertado por tener los
                recursos y ya cumplio el tiempo de espera
             */
            this.shotSemaphore.release();
            //this.conditionQueueLock.unlock();
        }

        else if (!Arrays.equals(new int[sensibilizadas.length],sensibilizadas)){
            /*Hay alguien para despertar, veo quien*/
            int nextAwake = policy.getNextAwake(sensibilizadas);

            colasCondicion.get(nextAwake).desencolar();
            //conditionQueueLock.unlock();
            shotSemaphore.release();
        }

        else{
            //conditionQueueLock.unlock();
            shotSemaphore.release();
            ingressSemaphore.release();
        }
    }
    private int []  awakeTemporal(int [] sensibilizadas){
        int [] nuevaSens = new int [sensibilizadas.length];
        int [] waitingVect = getWaitingVect();
        for (int i = 0; i < sensibilizadas.length; i++){
            if (sensibilizadas[i] > 1 && waitingVect[i] > 0){
                nuevaSens[i] = 0;
                colasCondicion.get(i).desencolar();
            }
            else
                nuevaSens[i] = sensibilizadas[i];
        }
        return nuevaSens;

    }

    /**
     *Recorre las cosas de condicion para ver si hay hilos esperando
     *
     * @return un vector binario con 1's en la pos N si hay hilos esperando en la cola N o 0's caso contrario
     */
    private int[] getWaitingVect(){

        int[] wv = new int[colasCondicion.size()];
        for (int i = 0; i < colasCondicion.size(); i++){
            if (colasCondicion.get(i).getQueueLen() > 0)
                wv[i] = 1;
        }
        return wv;
    }

    ///--------------------------------------------------------------------------------------

    //                          Disparo con tiempo

    //---------------------------------------------------------------------------------------



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
