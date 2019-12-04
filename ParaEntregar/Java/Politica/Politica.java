package Politica;
import Config.PolicyConfigurator;
import petriNet.MathOperator;

import java.security.InvalidAlgorithmParameterException;
import java.util.Random;

public class Politica {

    private final Random gen = new Random(System.currentTimeMillis()); //uso el tiempo para semilla
    private int next;
    private int [] priorities;
    private PoliticMode mode;

    /*public Politica(int [] pri, PoliticMode polMode){
        mode = polMode;
        next = -1;
        priorities =  pri;
    }*/

    public Politica(PolicyConfigurator policyConfigurator){
        this.mode = policyConfigurator.getMode();
        this.next = policyConfigurator.getNext();
        this.priorities = policyConfigurator.getPriorities();
    }

    /**
     * Retorna el numero de cola que se debe despertar
     *
     * @param sensibilizadas vector de transciciones sensibilizadas
     * @return numero de transcicion a despertar
     */
    public int getNextAwake(int [] sensibilizadas) throws InvalidAlgorithmParameterException {

        int nextAwake = 0;
        switch(mode){
            
            case RANDOM:
                nextAwake = getRandomNext(sensibilizadas);
                break;
            case ROND_ROBIN:
                getRoundRobinNext(sensibilizadas);
                nextAwake = next;
                break;
                    
            case HIGH_PRIO:
                nextAwake = getHighestPriorityNext(sensibilizadas);
                break;
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
    private void getRoundRobinNext(int [] sensibilizadas) {

        next++;
        if (next == sensibilizadas.length)
            next = 0;
        while (sensibilizadas[next] == 0) {
            next++;
            if (next == sensibilizadas.length)
                next = 0;
        }
    }

    /**
     * Obtener Nro de condicion(cola) que se debe despertar
     *
     * @param sensibilizadas Vector de 1's y 0's indicando que transc esta sensibilizada
     *
     * @return Numero de cola a la cual realizarle signal()
     */
    private int getHighestPriorityNext(int [] sensibilizadas) throws InvalidAlgorithmParameterException {

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
