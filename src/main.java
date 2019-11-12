import petriNet.*;
import Colas.*;

public class main {

    public static void main(String[] args){


        PetriNet rdp = new PetriNet("/home/ramiro/repos/Concurrente/src/petriNet/petriNet_parameters.json");


        int nplaces = rdp.getNplaces();
        int ntransitions = rdp.getNtransitions();
        int [][] petriNet_matrix = rdp.getIncidenceMatrix();

        for(int i = 0; i < nplaces; i++)
            for(int j = 0; j < ntransitions; j++) {
                System.out.printf("Fila %d: %s\n", i, petriNet_matrix[i][j]);
            }

        return;
    }
}

