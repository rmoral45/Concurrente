package petriNet;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
public class petriParser {

    private int [][] petriNet_matrix;
    private int [] initial_marking;

    public petriParser(String fileName) {

        try (FileReader reader = new FileReader(fileName))
        {
            //leemos json
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            //pedimos cantidad de columas de la matriz
            int ncols = jsonObject.getAsJsonObject("petriNet").get("columns").getAsInt();
            //pedimos la cantidad de filas de la matriz
            int nrows = jsonObject.getAsJsonObject("petriNet").get("rows").getAsInt();

            //leemos la matriz como vector de vectores
            JsonArray matrix_as_vector = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("incidence_matrix");
            this.petriNet_matrix = new int[nrows][ncols];
            JsonArray tmp = new JsonArray() ;
            for(int i = 0; i < nrows; i++)
                for(int j = 0; j < ncols; j++) {
                    tmp = matrix_as_vector.get(i).getAsJsonArray();
                    this.petriNet_matrix[i][j] = tmp.get(j).getAsInt();
                }

            //leemos vector de marcado inicial
            JsonArray init_mark_tmp = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("init_marking");
            for(int i = 0; i < nrows; i++)
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



