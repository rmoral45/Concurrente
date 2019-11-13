package Politica;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;
import java.util.Random;
import petriNet.MathOperator;

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
                    try {
                        getRoundRobinNext(sensibilizadas);
                        System.out.print("\nProximo hilo a despertar  " + next + "\n");
                        return next;
                    }catch (InvalidAlgorithmParameterException e){
                        System.exit(1);
                    }


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

    public void getRoundRobinNext(int [] sensibilizadas) throws InvalidAlgorithmParameterException {


        if (Arrays.equals(new int[sensibilizadas.length],sensibilizadas))
            throw new InvalidAlgorithmParameterException("Vecotr de sensibilizadas en 0");

        next++;
        //FIXME se deberia utilizar sensibilizadas.length ?
        if (next == numCondQueue)
            next = 0;
        while (sensibilizadas[next] == 0) {
            next++;
            if (next == numCondQueue)
                next = 0;
        }
    }

    private int getHighestPriorityNext(int [] sensibilizadas){
        return MathOperator.getMaxIndex(
                    MathOperator.andVector( sensibilizadas.length,
                                            priorities,
                                            sensibilizadas)
                                );

    }

    /*
        Desempata en caso de dos con iguales prioridades sensibilizdas
     */
    private int resolveTieByRandom(){
        return 1;
    }
}
