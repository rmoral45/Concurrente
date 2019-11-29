package Config;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

public class PetriNetConfigurator {

    //Vars for petriNet
    private int nplaces;
    private int ntransitions;
    private int npInvariants;
    private int [][] incidenceMatrix;
    private int [] initialMarking;
    private long [] alphaVector;
    private int [][] inibMatrix;
    private int [][] lectorMatrix;
    private int [][] placesInvariantsMatrix;
    private int [] placesInvariantsVector;


    /**
     * Clase destinada a configurar los parametros de la petriNet.
     * @param petriNet recibe el json de la petriNet y setea todos los parametros necesarios.
     */
    public PetriNetConfigurator(JsonObject petriNet) throws IOException {

        JsonArray matrix_as_vector = petriNet.getAsJsonArray("incidence_matrix");

        this.nplaces = matrix_as_vector.size();
        this.ntransitions = matrix_as_vector.get(0).getAsJsonArray().size();
        this.incidenceMatrix = new int[nplaces][ntransitions];

        //recorremos matrix_as_vector para inicializar la matriz de incidencia.
        JsonArray tmp = new JsonArray();
        for(int i = 0; i < this.nplaces; i++)
            for(int j = 0; j < this.ntransitions; j++) {
                tmp = matrix_as_vector.get(i).getAsJsonArray();
                this.incidenceMatrix[i][j] = tmp.get(j).getAsInt();
            }



        //inicializamos vector de marcado inicial
        this.initialMarking = new int[nplaces];
        JsonArray tmp_marking = petriNet.getAsJsonArray("init_marking");
        for(int k = 0; k < this.nplaces; k++)
            this.initialMarking[k] = tmp_marking.get(k).getAsInt();



        //inicializamos matriz de arcos inhibidores
        this.inibMatrix = new int[ntransitions][nplaces];
        JsonArray inib_arcs_tmp = petriNet.getAsJsonArray("inhib_matrix");
        JsonArray tmp_row;
        for(int i = 0; i < ntransitions; i++)
            for(int j = 0; j < nplaces; j++) {
                tmp_row = inib_arcs_tmp.get(i).getAsJsonArray();
                this.inibMatrix[i][j] = tmp_row.get(j).getAsInt();
            }



        //inicializamos matriz de arcos lectores
        this.lectorMatrix = new int[ntransitions][nplaces];
        JsonArray lector_arcs_tmp = petriNet.getAsJsonArray("lector_matrix");
        JsonArray tmp_row2;
        for(int i = 0; i < ntransitions; i++)
            for(int j = 0; j < nplaces; j++) {
                tmp_row2 = lector_arcs_tmp.get(i).getAsJsonArray();
                this.lectorMatrix[i][j] = tmp_row2.get(j).getAsInt();
            }

        //inicializamos vector de alphas
        this.alphaVector = new long[ntransitions];
        JsonArray tmp_alphas = petriNet.getAsJsonArray("alpha");
        for(int i = 0; i < ntransitions; i++) {
            this.alphaVector[i] = tmp_alphas.get(i).getAsLong();
            this.alphaVector[i] = tmp_alphas.get(i).getAsInt();
        }

        //leemos matriz de invariantes de plaza
        JsonArray pInvariants_matrix_tmp = petriNet.getAsJsonArray("p_invariants_matrix");
        this.npInvariants = pInvariants_matrix_tmp.size();
        this.placesInvariantsMatrix = new int[npInvariants][nplaces];
        JsonArray tmp_row3;
        for(int i = 0; i < npInvariants; i++)
            for(int j = 0; j < nplaces; j++){
                tmp_row3 = pInvariants_matrix_tmp.get(i).getAsJsonArray();
                this.placesInvariantsMatrix[i][j] = tmp_row3.get(j).getAsInt();
            }

        //leemos vector de invariantes de plaza
        //FIXME VERIFICAR SI ESTE VECTOR ES DE npInvariants o nplaces
        this.placesInvariantsVector = new int[npInvariants];
        JsonArray pInvariants_vector_tmp = petriNet.getAsJsonArray("p_invariants_vector");
        for(int i = 0; i < npInvariants; i++)
            this.placesInvariantsVector[i] = pInvariants_vector_tmp.get(i).getAsInt();

    }

    public int getNplaces() {
        return nplaces;
    }

    public int getNtransitions() {
        return ntransitions;
    }

    public int[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    public int[] getInitialMarking() {
        return initialMarking;
    }

    public long[] getAlphaVector() {
        return alphaVector;
    }

    public int[][] getLectorArcs() {
        return lectorMatrix;
    }

    public int[][] getInibArcs() {
        return inibMatrix;
    }

    public int[] getPlacesInvariantsVector() {
        return placesInvariantsVector;
    }

    public int[][] getPlacesInvariantsMatrix() {
        return placesInvariantsMatrix;
    }
}
