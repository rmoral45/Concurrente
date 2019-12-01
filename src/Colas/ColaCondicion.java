package Colas;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ColaCondicion {


    private final ArrayList<Long> threadsTimestamp = new ArrayList<Long>();
    private boolean safeGuard; //usada para prevenir "spurious wakeup"
    private int numCondicion;
    private int queueLen;
    private ReentrantLock conditionQueueLock ;
    private Condition resourceCondition;
    private Condition timeCondition;

    public ColaCondicion(int t) {
        conditionQueueLock = new ReentrantLock(true);
        resourceCondition = conditionQueueLock.newCondition();
        timeCondition = conditionQueueLock.newCondition();
        safeGuard = false;
        numCondicion = t;
        queueLen = 0;
    }

    public int getQueueLen(){
        return queueLen;
    }




    public void encolar (){
        conditionQueueLock.lock();
        safeGuard = true;
        threadsTimestamp.add(System.currentTimeMillis());
        queueLen++;
         try{
             while (safeGuard)
                 resourceCondition.await();
        }
         catch(InterruptedException e){
             System.exit(1);
         }
         finally{
             conditionQueueLock.unlock();

         }
    }

    public void encolarTemporal (long timeToSleep){
        conditionQueueLock.lock();
        try{
                timeCondition.await(timeToSleep, TimeUnit.MILLISECONDS);
        }
        catch(InterruptedException e){
            System.out.print("Error en encolaTemporal de la condicion " + numCondicion);
            System.exit(1);
        }
        finally {
            conditionQueueLock.unlock();
        }
    }

    public void desencolar(){
        //System.out.print("Desencolando cola :" + numCondicion + "\n");
        conditionQueueLock.lock();
        threadsTimestamp.remove(0);
        safeGuard = false;
        queueLen--;
        resourceCondition.signal();
        conditionQueueLock.unlock();
    }
}
