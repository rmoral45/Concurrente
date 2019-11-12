package petriNet;

public class PetriNet {

    private int[][] incidenceMatrix;
    private int[] mark_vector;

    public PetriNet(String fileName){

        MathOperator operator = new MathOperator();
        PetriParser parser = new PetriParser(fileName);
        this.incidenceMatrix = parser.getPetriNet_matrix();
        this.mark_vector = parser.getInitial_marking();
        //falta agregar arcos lectores y arcos inhibidores
    }
}
