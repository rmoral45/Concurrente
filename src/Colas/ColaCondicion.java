package Colas;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ColaCondicion {

    /*
        FIXME Los metodos y parametros relacionados al tiempo se van a implementar en otra clase que herede de esta
    */

    private Condition condition;
    private final ArrayList<Long> threadsTimestamp = new ArrayList<Long>();
    private boolean safeGuard; //usada para prevenir "spurious wakeup"
    private int numCondicion;
    private int queueLen;

    public ColaCondicion(Condition c,int t) {
        safeGuard = false;
        numCondicion = t;
        condition = c;
        queueLen = 0;
    }

    public int getQueueLen(){
        return queueLen;
    }


    public long getOldestTimestamp(){
        return threadsTimestamp.get(0);
    }

    public void encolar (){
        safeGuard = true;
        threadsTimestamp.add(System.currentTimeMillis());
        queueLen++;
        //System.out.print("Encolando\n");
         try{
             while (safeGuard)
                 condition.await();
        }
         catch(InterruptedException e){
             System.out.print("\n\n\nTu vieja\n\n\n");
         }
         finally{
             System.out.print("Hilo de la cola " + numCondicion + "Despertado\n");
         }
    }


    public void desencolar(){
        //System.out.print("Desencolando cola :" + numCondicion + "\n");
        threadsTimestamp.remove(0);
        safeGuard = false;
        queueLen--;
        condition.signal();
    }
}
