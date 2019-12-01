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

        policy = new Politica( new int [] {1,1,1,1,1,1,8,1,1,2,5,1,1,1,1,1}, polMode);
        petriNet = pn;
        K = false;
        logger = MyLoggerWrapper.getInstance("/home/dabratte/repos/Concurrente/log_files/pctemp.log");
        for (int i = 0; i < ntrans; i++) {
            ColaCondicion cc = new ColaCondicion(i);
            colasCondicion.add(cc);
        }

    }

    /**
     * Metodo para obtener recursos atraves del monitor, NO aplica Ec.Generalizada
     * @param numTranscicion numero de transicion que se desea disparar
     * @throws InterruptedException
     * @throws InvalidAlgorithmParameterException
     */
    public void dispararV2(int numTranscicion) throws InterruptedException, InvalidAlgorithmParameterException {

            ingressSemaphore.acquire();
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

            /* semaforo que toman los hilos que ya estan dentro del monitor*/
            shotSemaphore.acquire();

            long currentTime = System.currentTimeMillis();
            fr = petriNet.dispararExtendida(numTranscicion, currentTime);

            if (fr == FireResultType.SUCCESS){
                int [] sensibilizadas;
                sensibilizadas = petriNet.obtenerSensibilizadaExtendida(currentTime);

                /*despierto a todos los hilos que deben setear su propio tiempo*/
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
                wakeUp(new int [colasCondicion.size()]);
                colasCondicion.get(numTranscicion).encolarTemporal(petriNet.getRemainingTime(currentTime,numTranscicion));
            }

        }

    }


    /**
     * Metodo ejecutado por todos los hilos,ya sea que el disparo sea valido o no.
     * @param sensibilizadas vector de transciciones sensibilizadas
     * @throws InvalidAlgorithmParameterException
     */
    private void wakeUp(int [] sensibilizadas) throws InvalidAlgorithmParameterException {

        int [] wv = getWaitingVect();
        sensibilizadas = MathOperator.innerProdVector(wv,sensibilizadas);

        if (shotSemaphore.hasQueuedThreads()){
            /*
                Le cedo el monitor al hilo que fue despertado por tener los
                recursos y ya cumplio el tiempo de espera
             */
            this.shotSemaphore.release();

        }

        else if (!Arrays.equals(new int[sensibilizadas.length],sensibilizadas)){

            /*Hay alguien para despertar, veo quien*/
            int nextAwake = policy.getNextAwake(sensibilizadas);

            colasCondicion.get(nextAwake).desencolar();
            shotSemaphore.release();
        }

        else{
            /*No hay nadie adentro que pueda disparar, dejo entrar un nuevo hilo al monitor*/
            shotSemaphore.release();
            ingressSemaphore.release();
        }
    }

    /**
     * Despierta a todos los hilos que intentaron disparar transciciones temporales
     *  y no pudieron por falta de recursos
     * @param sensibilizadas vector de sensibilizados
     * @return vector de sensibilizados modificado para no contener hilos de trans temporales
     */
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

}
