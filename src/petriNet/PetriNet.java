package petriNet;
import Parser.*;

public class PetriNet {

    private int nplaces;
    private int ntransitions;
    //private int[][] incidenceMatrix;
    public int [][] incidenceMatrix;
    private int[] mark_vector;

    //[Diego] Agrego variables necesarias para RdP Temporales

    private boolean [] esperando; // Indica si algun hilo quiso dipararla,y por lo tanto el timestamp es valido
    private long    [] transitionTimeStamp; //timeStamp del momento cuando quiso ser disprada
    private boolean [] isTemporal; //indica si la transicion es temporizada
    private long    [] alpha; //contiene en limite inferior del intervalo temporal para cada transcision;



    public PetriNet(String fileName){

        Parser parser = new Parser(fileName);
        this.nplaces = parser.getNplaces();
        this.ntransitions = parser.getNtransitions();
        this.incidenceMatrix = parser.getPetriNet_matrix();
        this.mark_vector = parser.getInitial_marking();
        //falta agregar arcos lectores y arcos inhibidores
    }

    int getNtransitions() {
        return ntransitions;
    }

    int getNplaces() {
        return nplaces;
    }

    int[] getMark_vector() {
        return mark_vector;
    }

    int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    /**
     * habilitarTransicion - dispara una transicion SIN CAMBIAR EL ESTADO DE LA RED
     * dispara la transicion pasada como argumento
     * [Diego] Esta funcion deberia aplicar la Ec. de Estado Generalizada
     * EXCEPTO la parte temporal !!
     * @param transition numero de transicion a disparar.
     * @return int[] - retorna el vector de marcado obtenido al disparar la transicion.
     */
    public int[] probarDisparo(int transition){

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;

        int [] new_marking;
        new_marking = MathOperator.vectmatProd(this.incidenceMatrix, vector_de_disparo);
        new_marking = MathOperator.addVector(new_marking,this.mark_vector);

        return new_marking;
    }

    
    /**
     * Revisa si se cumplio el timestamp para una transcion temporal que ya quiso ser disparada
     *
     */
    private boolean testTimeWindow(int transition){

        long currentTime = System.currentTimeMillis();
        if (alpha[transition] > 0 && esperando[transition]){

            return ((currentTime - transitionTimeStamp[transition]) > alpha[transition]);
        }
        else {
            return true;
        }

    }

    /**
     *Toda transcicion puede ser definida coomo temporal, en el caso de una transcicion
     * con unintervalo [0, infinito] es una transcicion 'normal'
     */

    private boolean dispararTemporal(int transition){
            //Verifico que los recursos enten disponibles
        int[] new_marking  = this.probarDisparo(transition);

        if(MathOperator.HasNegative(new_marking))
            return false;
        else {
            if (alpha[transition] == 0){
                // No es una transcicion  temporal, por lo tanto solo debe haberse cumplido
                // que los recursos esten disponibles
                this.mark_vector = new_marking;
                return true;
            }
            else{
                esperando[transition] = true;
                transitionTimeStamp[transition] = System.currentTimeMillis();
                return false;
            }
        }

    }

    private void setEsperando(int transition, boolean val){
        esperando[transition] = val;
    }


    public int[] obtenerSensTemporal(int [] waiting){

        /*
            Una transcion puede sensibilarse xq hubo un cambio de estado en el sistema,
            si la transcicion debio esperar un tiempo puede que al momento de dormirse los recursos
            hayan estado pero se los haya llevado algun otro hilo antes que se cumpla el tiempo 
        */

        int [] sensibilizadas = new int[this.ntransitions];
        int [] tmp_vector;

        for(int i = 0; i < this.ntransitions; i++) {
            if (waiting[i] == 1){
                tmp_vector = probarDisparo(i);
                if ( !MathOperator.HasNegative(tmp_vector) && testTimeWindow(i) )
                    sensibilizadas[i] = 1;
                else
                    sensibilizadas[i] = 0;
            }
        }

         return  sensibilizadas;
    }
}
