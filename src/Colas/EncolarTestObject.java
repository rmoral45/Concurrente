package Colas;

import java.util.concurrent.locks.ReentrantLock;

public class EncolarTestObject implements Runnable{

    private ColaCondicion cola;
    private ReentrantLock lock;

    public EncolarTestObject(ColaCondicion cc, ReentrantLock l){
        cola = cc;
        lock = l;
    }

    @Override
    public void run() {
        lock.lock();
        this.cola.encolar();
        lock.unlock();
    }


}
