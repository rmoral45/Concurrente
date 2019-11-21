package Parser;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PetriNetConfigurator {

    //Vars for petriNet
    private int nplaces;
    private int ntransitions;
    private int [][] incidence_matrix;
    private int [] initial_marking;
    private int [] inib_arcs;
    private int [] lector_arcs;

    /**
     * Clase destinada a configurar los parametros de la petriNet.
     * @param petriNet recibe el json de la petriNet y setea todos los parametros necesarios.
     */
    public PetriNetConfigurator(JsonObject petriNet){

        JsonArray matrix_as_vector = petriNet.getAsJsonArray("incidence_matrix");

        this.nplaces = matrix_as_vector.size();
        this.ntransitions = matrix_as_vector.get(0).getAsJsonArray().size();
        this.incidence_matrix = new int[nplaces][ntransitions];

        //recorremos matrix_as_vector para inicializar la matriz de incidencia.
        JsonArray tmp = new JsonArray();
        for(int i = 0; i < this.nplaces; i++)
            for(int j = 0; j < this.ntransitions; j++) {
                tmp = matrix_as_vector.get(i).getAsJsonArray();
                this.incidence_matrix[i][j] = tmp.get(j).getAsInt();
            }

        //inicializamos vector de marcado inicial
        this.initial_marking = new int[nplaces];
        JsonArray tmp_marking = petriNet.getAsJsonArray("init_marking");
        for(int k = 0; k < this.nplaces; k++)
            this.initial_marking[k] = tmp_marking.get(k).getAsInt();


        //inicializamos vector de arcos inhibidores
        this.inib_arcs = new int[ntransitions];
            /*JsonArray inib_arcs_tmp = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("inib_arcs");
              for(int i = 0; i < nplaces; i++)
                this.inib_arcs[i] = inib_arcs_tmp.get(i).getAsInt();
             */

        //inicializamos vector de arcos lectores
        this.lector_arcs = new int[ntransitions];
            /*JsonArray lector_arcs_tmp = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("inib_arcs");
              for(int i = 0; i < nplaces; i++)
                this.lector_arcs[i] = lector_arcs_tmp.get(i).getAsInt();
             */
    }

    public int getNplaces() {
        return nplaces;
    }

    public int getNtransitions() {
        return ntransitions;
    }

    public int[][] getIncidence_matrix() {
        return incidence_matrix;
    }

    public int[] getInitial_marking() {
        return initial_marking;
    }

    public int[] getLector_arcs() {
        return lector_arcs;
    }

    public int[] getInib_arcs() {
        return inib_arcs;
    }
}
