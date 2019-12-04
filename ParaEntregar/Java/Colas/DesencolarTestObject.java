package Colas;

import java.util.concurrent.locks.ReentrantLock;

public class DesencolarTestObject implements Runnable {

    private ColaCondicion cola;
    private ReentrantLock lock;
    private int ntrhread;

    public DesencolarTestObject(ColaCondicion cc, ReentrantLock l, int nth){
        cola = cc;
        lock = l;
        ntrhread = nth;
    }
    @Override
    public void run() {
        //lock.lock();
        for (int i=0; i<ntrhread; i++)
            this.cola.desencolar();

        //lock.unlock();
    }
}
