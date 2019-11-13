package Colas;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

class ColaCondicionTest {


    @Test
    void setSleepingThreads(){
        int numCond = 5;
        int nthread = 4;
        ReentrantLock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        ColaCondicion cola = new ColaCondicion(condition,numCond);
        try{
            for (int i=0; i<nthread; i++) {
                Thread th = new Thread(new EncolarTestObject(cola,lock));
                th.start();
                Thread.sleep(300);
            }
            lock.lock();
            assertEquals(nthread, cola.getQueueLen());
            assertEquals(nthread, lock.getWaitQueueLength(condition));
            lock.unlock();
            Thread.sleep(1000);
            (new Thread(new DesencolarTestObject(cola,lock,nthread))).start();
            Thread.sleep(1000);
            lock.lock();
            assertEquals(0, cola.getQueueLen());
            assertEquals(0, lock.getWaitQueueLength(condition));
            lock.unlock();

        }
        catch (InterruptedException e){
            fail();
        }

    }



}