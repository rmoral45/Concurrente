package petriNet;
import Parser.*;

public class PetriNet {

    private int nplaces;
    private int ntransitions;
    //private int[][] incidenceMatrix;
    public int [][] incidenceMatrix;
    private int[] mark_vector;

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
     *
     * @param transition numero de transicion a disparar.
     * @return int[} - retorna el vector de marcado obtenido al disparar la transicion.
     */
    public int[] probarDisparo(int transition){

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;

        int [] new_marking;
        new_marking = MathOperator.vectmatProd(this.incidenceMatrix, vector_de_disparo);

        return new_marking;
    }

    /**
     * dispararTransicion - verifica si la transicion es disparable, si lo es cambia el estado del sistema
     * @param transition numero de transicion a disparar
     * @return  true: si se pudo disparar la transicion
     *          false: si no se pudo disparar la transicion
     */
    public boolean dispararTransicion(int transition){

        int[] new_marking  = this.probarDisparo(transition);

        for(int i = 0; i < new_marking.length; i++)
            System.out.printf("NUEVO MARCADO - PROBAR DISPARO: Fila %d: %d\n", i, new_marking[i]);

        new_marking = MathOperator.addVector(new_marking,this.mark_vector);

        for(int i = 0; i < new_marking.length; i++)
            System.out.printf("NUEVO MARCADO - ADD VECTOR: Fila %d: %d\n", i, new_marking[i]);

        System.out.printf("El Resultado del hasnegative() es: %b", MathOperator.HasNegative(new_marking));

        if(MathOperator.HasNegative(new_marking))
            return false;
        else {
            this.mark_vector = new_marking;
            return true;
        }
    }

    /**
     * obtenerSensibilidazas - verifica cuales de todas las transiciones de la red estan sensibilizadas
     * @return int[]: vector con 1 en cuyas posiciones la transicion se encuentra sensibilizada, por ejemplo
     *                si se tienen 6 transiciones y solo la 2 y la 5 estan sensibilizadas retorna [0,0,1,0,0,1]
     */

    //[FIX] agregue para que solo pregunte por las transciciones que tienen hilos esperando
    
    public int[] obtenerSensibilizadas(int [] waiting){

        int [] sensibilizadas = new int[this.ntransitions];
        int [] tmp_vector;

        for(int i = 0; i < this.ntransitions; i++) {
            if (waiting[i] == 1){
                tmp_vector = probarDisparo(i);
                if (MathOperator.HasNegative(tmp_vector))
                    sensibilizadas[i] = 0;
                else
                    sensibilizadas[i] = 1;
        }
        }
         return  sensibilizadas;
    }
}
