package petriNet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class PetriParser {

    private int [] initial_marking;
    private int [][] petriNet_matrix;


    public PetriParser(String fileName) {

        try (FileReader reader = new FileReader(fileName))
        {
            //leemos json
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            //leemos la matriz como vector de vectores
            JsonArray matrix_as_vector = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("incidence_matrix");
            //Cantidad de plazas es la cantidad de filas de la matriz
            int nplaces = matrix_as_vector.size();
            //Cantidad de trancisiones es cantidad de elementos de una fila de la matriz
            int ntransitions = matrix_as_vector.get(0).getAsJsonArray().size();

            System.out.printf("cantidad de plazas: %d\n", nplaces);
            System.out.printf("cantidad de transiciones: %d\n", ntransitions);


            this.petriNet_matrix = new int[nplaces][ntransitions];
            JsonArray tmp = new JsonArray();
            for(int i = 0; i < nplaces; i++)
                for(int j = 0; j < ntransitions; j++) {
                    tmp = matrix_as_vector.get(i).getAsJsonArray();
                    this.petriNet_matrix[i][j] = tmp.get(j).getAsInt();
                }

            this.initial_marking = new int[nplaces];
            //leemos vector de marcado inicial
            JsonArray init_mark_tmp = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("init_marking");
            for(int i = 0; i < nplaces; i++)
                this.initial_marking[i] = init_mark_tmp.get(i).getAsInt();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[][] getPetriNet_matrix(){
        return this.petriNet_matrix;
    }

    public int[] getInitial_marking() {
        return initial_marking;
    }
}



