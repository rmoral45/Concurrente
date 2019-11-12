package petriNet;

public class petriNet {

    private int[][] incidenceMatrix;
    private int[] mark_vector;

    public petriNet(String fileName){

        mathOperator operator = new mathOperator();
        petriParser parser = new petriParser(fileName);
        this.incidenceMatrix = parser.getPetriNet_matrix();
        this.mark_vector = parser.getInitial_marking();
        //falta agregar arcos lectores y arcos inhibidores
    }
}
