package petriNet;

import Config.PetriNetConfigurator;
import MyLogger.MyLoggerWrapper;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.util.Arrays;

public class PetriNet {

    private int nplaces;
    private int ntransitions;
    private int [][] incidenceMatrix;
    private int [][] H;
    private int [][] R;
    private int [] markVector;
    private int [][] pInvarianMatrix;
    private int []   pInvarianVector;

    //[Diego] Agrego variables necesarias para RdP Temporales

    private boolean [] validTimeStamp; // Indica si se senbilizo por algun disparo, por ende su timestamp fue seteado
    private long    [] transitionTimeStamp; //timeStamp del momento cuando quiso ser disprada
    private long    [] alpha; //contiene en limite inferior del intervalo temporal para cada transcision;
    private MyLoggerWrapper logger;



    public PetriNet(PetriNetConfigurator pnConfig, boolean enableLog, String logPath) throws IOException {

        this.nplaces = pnConfig.getNplaces();
        this.ntransitions = pnConfig.getNtransitions();
        this.incidenceMatrix = pnConfig.getIncidenceMatrix();
        this.markVector = pnConfig.getInitialMarking();
        this.H = pnConfig.getInibArcs();
        this.R = pnConfig.getLectorArcs();
        this.alpha = pnConfig.getAlphaVector();
        this.pInvarianMatrix = pnConfig.getPlacesInvariantsMatrix();
        this.pInvarianVector = pnConfig.getPlacesInvariantsVector();
        this.transitionTimeStamp = new long [ntransitions];
        this.validTimeStamp = new boolean [ntransitions];
        //falta agregar arcos lectores y arcos inhibidores
        if (enableLog)
            logger = MyLoggerWrapper.getInstance(logPath);
        else
            logger = null;


    }

    public int getNtransitions() {
        return ntransitions;
    }

    public int getNplaces() {
        return nplaces;
    }

    public int[] getMarkVector() {
        return markVector;
    }

    public int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    /**
     * dispara una transicion SIN CAMBIAR EL ESTADO DE LA RED
     * Solamente aplica la ecuacion para obtener el nuevo marcado (I x Sigma) + Mj
     * EXCEPTO la parte temporal !!
     * @param transition numero de transicion a disparar.
     * @return int[] - retorna el vector de marcado obtenido al disparar la transicion. Mj+1
     */
    private int[] probarDisparo(int transition) throws InvalidAlgorithmParameterException {

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;

        int [] new_marking;
        new_marking = MathOperator.vectmatProd(this.incidenceMatrix, vector_de_disparo);
        new_marking = MathOperator.addVector(new_marking, this.markVector);
        return new_marking;

    }




    public boolean dispararTransicion(int transition) throws InvalidAlgorithmParameterException {
        //Verifico que los recursos enten disponibles
        int[] new_marking  = this.probarDisparo(transition);

        if(MathOperator.HasNegative(new_marking))
            return false;
        else {
            // No es una transcicion  temporal, por lo tanto solo debe haberse cumplido
            // que los recursos esten disponibles
            this.markVector = new_marking;
            if (logger != null) {
                logger.myLogger.info("[disparo : " + transition + "; marcado : "
                                     + Arrays.toString(this.markVector) + " ]");
            }

            return true;
        }


    }

    /**
     * Obtiene transciciones que fueron sensibilizadas a partir de la Ec estado
     * @param waiting vector de 1's y 0's indicando si hay o no alguien esperando para disparar
     * @return vector de 1's y 0's indicando las transciciones que pueden ser disparadas
     * @throws InvalidAlgorithmParameterException
     */
    public int[] obtenerSensibilizadas(int [] waiting) throws InvalidAlgorithmParameterException {

        int [] sensibilizadas = new int[this.ntransitions];
        int [] tmp_vector;

        for(int i = 0; i < waiting.length; i++) {
            if (waiting[i] == 1){
                tmp_vector = probarDisparo(i);
                if ( !MathOperator.HasNegative(tmp_vector)) {
                    sensibilizadas[i] = 1;
                }
                else {
                    sensibilizadas[i] = 0;
                }
            }
        }

        return  sensibilizadas;
    }

    private void testInvariantesPlaza(int numTransicion) throws Exception {
        int acc;
        int [] tmp;
        for (int i = 0; i < pInvarianMatrix.length; i++){
            tmp = MathOperator.innerProdVector(pInvarianMatrix[i], markVector);
            acc = MathOperator.addElements(tmp);
            if (acc != pInvarianVector[i])
                throw new IllegalStateException("Fallo test de invariantes en disparo de transcicion :" + numTransicion);
        }
    }

/*
  -----------------------------------------------------------------------------------------
  -----------------------------------------------------------------------------------------


                Metodos para RDP con Semantica Extendida

                        -Arcos Inhibidores

                        -Arcos Lectores

                        -Transiciones Temporales


 ------------------------------------------------------------------------------------------
 ------------------------------------------------------------------------------------------
 */


    /**
     * Toda transcicion puede ser definida coomo temporal, en el caso de una transcicion
     * con unintervalo [0, infinito] es una transcicion 'normal'
     */

    public FireResultType dispararExtendida(int transition, long currentTime) throws Exception {



        boolean validFire;
        int [] posibleMark;
        int [] habilitadasPorInhibidor;
        int [] habilitadasPorLector;
        int [] Q;
        int [] W;

       /*
            Calculo Q y W
        */
        Q = MathOperator.uno(markVector);
        W = MathOperator.cero(markVector);

        /*
            Aplico Ec para arcos ihbidores y lectores
         */
        habilitadasPorInhibidor = MathOperator.vectmatProd(H,Q);
        habilitadasPorLector    = MathOperator.vectmatProd(R,W);

        /*
            Convierto a vectores de 1's y 0's
         */
        habilitadasPorInhibidor = MathOperator.binarizeVect(habilitadasPorInhibidor);
        habilitadasPorLector    = MathOperator.binarizeVect(habilitadasPorLector);

        /*
            Niego los vectores
         */
        habilitadasPorInhibidor = MathOperator.negateVect(habilitadasPorInhibidor);
        habilitadasPorLector    = MathOperator.negateVect(habilitadasPorLector);
        posibleMark = probarDisparo(transition);

        validFire = MathOperator.sign(posibleMark)
                    * habilitadasPorInhibidor[transition]
                    * habilitadasPorLector[transition] == 1;

        if(!validFire)
            return FireResultType.RESOURCE_UNAVAILABLE;


        else if((currentTime - transitionTimeStamp[transition]) < alpha[transition]
                && validTimeStamp[transition])
            return FireResultType.TIME_DISABLED;

        /* Ya dispare la transcicion temporal que habia sido sensibilizada,
            por ende el timeStamp que fue seteado con anterioridad ya no es valido  */

        validTimeStamp[transition] = false;
        this.markVector = posibleMark;
        testInvariantesPlaza(transition);
        if (logger != null) {
            logger.myLogger.info("{\"disparo\" : " + "\"" + transition +"\"" +  ", \"marcado\" : "
                    + Arrays.toString(this.markVector) + " }");
        }
        return FireResultType.SUCCESS;

    }

    /**
     * Este metodo no se puede optimizar usando el vector waiting[] ya que se deben
     * probar las transciciones temporales, aunque no haya ningun hilo esperando para
     * dispararlas
     * @return vector con 0's si no esta sens, 1's si esta sensibilizada y 2's si paso de no sensibilizada a sensibilizada
     */

    public int[] obtenerSensibilizadaExtendida(long currentTime) throws InvalidAlgorithmParameterException {
        /*
            Verificar si la que fue sensibilizada ya se le habia seteado el timeStamp valido
            entonces no actualizar
         */

        int [] sens = new int[this.ntransitions];
        int [] posibleMark;
        int [] habilitadasPorInhibidor;
        int [] habilitadasPorLector;
        int [] Q;
        int [] W;

        /*
            Calculo Q y W
        */
        Q = MathOperator.uno(markVector);
        W = MathOperator.cero(markVector);

        /*
            Aplico Ec para arcos ihbidores y lectores
         */
        habilitadasPorInhibidor = MathOperator.vectmatProd(H,Q);
        habilitadasPorLector    = MathOperator.vectmatProd(R,W);

        /*
            Convierto a vectores de 1's y 0's
         */
        habilitadasPorInhibidor = MathOperator.binarizeVect(habilitadasPorInhibidor);
        habilitadasPorLector    = MathOperator.binarizeVect(habilitadasPorLector);

        /*
            Niego los vectores
         */
        habilitadasPorInhibidor = MathOperator.negateVect(habilitadasPorInhibidor);
        habilitadasPorLector    = MathOperator.negateVect(habilitadasPorLector);


        for(int i = 0; i < this.ntransitions; i++) {
            posibleMark = probarDisparo(i);
            sens[i] = MathOperator.sign(posibleMark) * habilitadasPorInhibidor[i] * habilitadasPorLector[i];
            //Verificar si ya estaba sensibilizada de antes
            if (sens[i] == 1 && !validTimeStamp[i] && alpha[i] > 0){
                validTimeStamp[i] = true;
                transitionTimeStamp[i] = currentTime;
                sens[i] = 2; //Verdaderamente no esta sensibilizada
            }


        }

         return  sens;
    }

    public long getRemainingTime(long currTime, int transition){
        return ((transitionTimeStamp[transition] + alpha[transition]) - currTime );
    }

    public long getAlpha(int transition){
        return alpha[transition];
    }
}
