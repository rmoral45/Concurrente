package Politica;
import java.util.ArrayList;
import java.util.Random;

public class Politica {

    private final Random gen = new Random(System.currentTimeMillis()); //uso el tiempo para semilla
    private int next;
    private int numCondQueue;
    private int [] priorities;
    private PoliticMode mode;

    public Politica(int nqueue, PoliticMode polMode){
        numCondQueue = nqueue;
        mode = polMode;
        next = -1;
        priorities = new int[nqueue];
        for (int i=0; i< nqueue; i++)
            priorities[i] = 0;
    }

    public int getNextAwake(int [] sensibilizadas){

        switch(mode){
            case RANDOM:
                return getRandomNext(sensibilizadas);
            case ROND_ROBIN:
                getRoundRobinNext(sensibilizadas);
                System.out.print("\nProximo hilo a despertar  " + next + "\n");
                return next;

        }
        return 0; //FIXME ver como cumplir todos los casos
    }

    private int getRandomNext(int [] sensibilizadas){
        int nextAwake;
        do{
            nextAwake = gen.nextInt(numCondQueue);
        }while(sensibilizadas[nextAwake] == 0);
        return nextAwake;
    }

    private void getRoundRobinNext(int [] sensibilizadas){
        /*
            FIXME si sensibilizadas en todos 0's se rompe en el while
        */
        next++;
        if (next == numCondQueue)
            next = 0;
        while (sensibilizadas[next] == 0) {
            next++;
            if (next == numCondQueue)
                next = 0;
        }
    }
}
