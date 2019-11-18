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

    public Politica(int [] pri, PoliticMode polMode){
        //numCondQueue = nqueue; //Fixme eliminar mas adelante
        mode = polMode;
        next = -1;
        priorities =  pri;
        /*priorities = new int[nqueue];
        for (int i=0; i< nqueue; i++)
            priorities[i] = 0;*/
    }

    /**
     * Retorna el numero de cola que se debe despertar
     *
     * @param sensibilizadas
     * @return
     */
    public int getNextAwake(int [] sensibilizadas){

        int nextAwake = 0;
        switch(mode){
            
            case RANDOM:
                nextAwake = getRandomNext(sensibilizadas);
                break;
            case ROND_ROBIN:
                    try {
                        getRoundRobinNext(sensibilizadas);
                        return next;
                    }catch (InvalidAlgorithmParameterException e){
                        System.exit(1);
                    }
                    nextAwake = next;
                    break;
                    
            case HIGH_PRIO:
                nextAwake = getHighestPriorityNext(sensibilizadas);
                break;

            /*default:
                nextAwake = getRandomNext(sensibilizadas);
                return  nextAwake;*/
        }
        return nextAwake;
    }

    /**
     * Elige alguna de las sensibilizadas de forma aleatoria
     *
     * @param  sensibilizadas Vector de 1's y
     *
     * @return  Numero de cola a la cual realizarle signal()
     */
    private int getRandomNext(int [] sensibilizadas){

        int nextAwake;
        do {
            nextAwake = gen.nextInt(sensibilizadas.length);
        } while (sensibilizadas[nextAwake] == 0);

        return nextAwake;

    }

    /**
     *
     * @param sensibilizadas Vector con 1's y 0's indicando si la transcicion esta sensibilizada
     */
    private int getRoundRobinNext(int [] sensibilizadas) throws InvalidAlgorithmParameterException {

        next++;
        //FIXME se deberia utilizar sensibilizadas.length ?
        if (next == sensibilizadas.length)
            next = 0;
        while (sensibilizadas[next] == 0) {
            next++;
            if (next == sensibilizadas.length)
                next = 0;
        }
        return next;
    }

    /**
     * Obtener Nro de condicion(cola) que se debe despertar
     *
     * @param sensibilizadas Vector de 1's y 0's indicando que transc esta sensibilizada
     *
     * @return Numero de cola a la cual realizarle signal()
     */
    private int getHighestPriorityNext(int [] sensibilizadas){

        int [] availables = MathOperator.getMaxIndexVect( MathOperator.innerProdVector(sensibilizadas, priorities));
        if (availables.length == 1)
            return availables[0];
        else{ //Desempatar
            return resolveTieByRandom(availables);
        }

    }

    /*
        Desempata en caso de dos con iguales prioridades sensibilizdas
     */
    private int resolveTieByRandom(int [] vect){
        return gen.nextInt(vect.length);
    }
}
