package petriNet;

import java.util.Arrays;

public class PetriNet {

    private int nplaces;
    private int ntransitions;
    private int[][] incidenceMatrix;
    private int[] mark_vector;
    private MathOperator operator;

    public PetriNet(String fileName){

        this.operator = new MathOperator();
        PetriParser parser = new PetriParser(fileName);
        this.nplaces = parser.getNplaces();
        this.ntransitions = parser.getNtransitions();
        this.incidenceMatrix = parser.getPetriNet_matrix();
        this.mark_vector = parser.getInitial_marking();
        //falta agregar arcos lectores y arcos inhibidores
    }

    public int getNtransitions() {
        return ntransitions;
    }

    public int getNplaces() {
        return nplaces;
    }

    public int[] getMark_vector() {
        return mark_vector;
    }

    public int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    public boolean dispararTransicion(int transition){

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;

        int [] actual_marking = this.mark_vector;

        int [] new_marking = new int[nplaces];

        new_marking = this.opera
    }


}
