package petriNet;
import Parser.*;

import java.security.InvalidAlgorithmParameterException;

public class PetriNet {

    private int nplaces;
    private int ntransitions;
    private int[][] incidenceMatrix;
    private int [][] H;
    private int [][] R;
    private int []   mark_vector;

    //[Diego] Agrego variables necesarias para RdP Temporales

    private boolean [] validTimeStamp; // Indica si se senbilizo por algun disparo, por ende su timestamp fue seteado
    private long    [] transitionTimeStamp; //timeStamp del momento cuando quiso ser disprada
    private long    [] alpha; //contiene en limite inferior del intervalo temporal para cada transcision;



    public PetriNet(PetriNetConfigurator pnConfig){

        //FIXME Inicializar alpha, timestamp y esperando
        this.nplaces = pnConfig.getNplaces();
        this.ntransitions = pnConfig.getNtransitions();
        this.incidenceMatrix = pnConfig.getIncidence_matrix();
        this.mark_vector = pnConfig.getInitial_marking();
        //falta agregar arcos lectores y arcos inhibidores


    }

    public int getNtransitions() {
        return ntransitions;
    }

    int getNplaces() {
        return nplaces;
    }

    public int[] getMark_vector() {
        return mark_vector;
    }

    int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    /**
     *
     * @param wanted vector de 1's y 0's indicando si la plaza debe sumarse o no
     * @return suma del marcado en todas las plazas deseadas
     */
    public int getMarcAddition(int [] wanted){
        int addition = 0;
        for (int i=0; i< wanted.length; i++){
            if (wanted[i] == 1)
                addition += mark_vector[i];
        }
        return addition;
    }
    /**
     * dispara una transicion SIN CAMBIAR EL ESTADO DE LA RED
     * Solamente aplica la ecuacion para obtener el nuevo marcado (I x Sigma) + Mj
     * EXCEPTO la parte temporal !!
     * @param transition numero de transicion a disparar.
     * @return int[] - retorna el vector de marcado obtenido al disparar la transicion. Mj+1
     */
    public int[] probarDisparo(int transition) throws InvalidAlgorithmParameterException {

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;

        int [] new_marking;
        new_marking = MathOperator.vectmatProd(this.incidenceMatrix, vector_de_disparo);
        new_marking = MathOperator.addVector(new_marking, this.mark_vector);
        return new_marking;

    }


    /**
     * Revisa si se cumplio el timestamp para una transcion temporal que ya quiso ser disparada
     *
     */
    private boolean testTimeWindow(int transition){

        long currentTime = System.currentTimeMillis();
        if (alpha[transition] > 0 && validTimeStamp[transition]){

            return ((currentTime - transitionTimeStamp[transition]) > alpha[transition]);
        }
        else {
            return true;
        }

    }


    public boolean dispararTransicion(int transition) throws InvalidAlgorithmParameterException {
        //Verifico que los recursos enten disponibles
        int[] new_marking  = this.probarDisparo(transition);

        if(MathOperator.HasNegative(new_marking))
            return false;
        else {
            // No es una transcicion  temporal, por lo tanto solo debe haberse cumplido
            // que los recursos esten disponibles
            this.mark_vector = new_marking;
            return true;
        }


    }

    public int[] obtenerSensibilizadas(int [] waiting) throws InvalidAlgorithmParameterException {

        /*
            Una transcion puede sensibilarse xq hubo un cambio de estado en el sistema,
            si la transcicion debio esperar un tiempo puede que al momento de dormirse los recursos
            hayan estado pero se los haya llevado algun otro hilo antes que se cumpla el tiempo
        */
        int [] sensibilizadas = new int[this.ntransitions];
        //int [] sensibilizadas = new int[waiting.length];
        int [] tmp_vector;

        //for(int i = 0; i < this.ntransitions; i++) {
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


    public int[] probarDisparoExtendida(int transition) throws InvalidAlgorithmParameterException {

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;

        int [] new_marking;
        new_marking = MathOperator.vectmatProd(this.incidenceMatrix, vector_de_disparo);
        new_marking = MathOperator.addVector(new_marking,this.mark_vector);

        return new_marking;
    }

    /**
     * Toda transcicion puede ser definida coomo temporal, en el caso de una transcicion
     * con unintervalo [0, infinito] es una transcicion 'normal'
     */

    public FireResultType dispararExtendida(int transition, long currentTime) throws InvalidAlgorithmParameterException {

        boolean validFire;
        int[] new_marking  = this.probarDisparoExtendida(transition);
        int [] posibleMark;
        int [] habilitadasPorInhibidor;
        int [] habilitadasPorLector;
        int [] Q;
        int [] W;

        /*
            Calculo Q y W
        */
        //FIXME revisar el calculo de Q y W por que creo que estaba mal el paper
        Q = MathOperator.cero(mark_vector);
        W = MathOperator.uno(mark_vector);
        habilitadasPorInhibidor = MathOperator.vectmatProd(H,Q);
        habilitadasPorLector    = MathOperator.vectmatProd(R,W);
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
        this.mark_vector = posibleMark;
        return FireResultType.SUCCESS;

    }

    /**
     * Este metodo no se puede optimizar usando el vector waiting[] ya que se deben
     * probar las transciciones temporales, aunque no haya ningun hilo esperando para
     * dispararlas
     * @return
     */

    public int[] obtenerSensibilizadaExtendida(long currentTime) throws InvalidAlgorithmParameterException {
        /*
            Verificar si la que fue sensibilizada ya se le habia seteado el timeStamp valido
            entonces no actualizar
         */

        int [] sensibilizadas = new int[this.ntransitions];
        int [] posibleMark;
        int [] habilitadasPorInhibidor;
        int [] habilitadasPorLector;
        int [] Q;
        int [] W;

        /*
            Calculo Q y W
        */
        Q = MathOperator.uno(mark_vector);
        W = MathOperator.cero(mark_vector);

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
            sensibilizadas[i] = MathOperator.sign(posibleMark) * habilitadasPorInhibidor[i] * habilitadasPorLector[i];
            //Verificar si ya estaba sensibilizada de antes
            if (sensibilizadas[i] == 1 && !validTimeStamp[i]){
                validTimeStamp[i] = true;
                transitionTimeStamp[i] = currentTime;
                sensibilizadas[i] = 0; //Verdaderamente no esta sensibilizada
            }


        }

         return  sensibilizadas;
    }
}
