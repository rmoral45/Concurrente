package petriNet;

public class PetriNet {

    private int nplaces;
    private int ntransitions;
    private int[][] incidenceMatrix;
    private int[] mark_vector;

    public PetriNet(String fileName){

        PetriParser parser = new PetriParser(fileName);
        this.nplaces = parser.getNplaces();
        this.ntransitions = parser.getNtransitions();
        this.incidenceMatrix = parser.getPetriNet_matrix();
        this.mark_vector = parser.getInitial_marking();
        //falta agregar arcos lectores y arcos inhibidores
    }

    private int getNtransitions() {
        return ntransitions;
    }

    private int getNplaces() {
        return nplaces;
    }

    private int[] getMark_vector() {
        return mark_vector;
    }

    private int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    public boolean dispararTransicion(int transition){

        int [] vector_de_disparo = new int [ntransitions];
        vector_de_disparo[transition] = 1;
        boolean flag = false;

        int [] actual_marking = this.mark_vector;
        int [] new_marking = new int[nplaces];

        new_marking = MathOperator.vectmatProd(this.incidenceMatrix, vector_de_disparo);
        for(int i = 0; i < nplaces; i++)
            new_marking[i] = new_marking[i] + actual_marking[i];


        flag = (new_marking[transition] < 0) ? false : true;

        if(flag)
            this.mark_vector = new_marking;

        return flag;
    }
}
