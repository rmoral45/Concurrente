import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class petriParser {

    public petriParser(String fileName) {



        try (FileReader reader = new FileReader(fileName))
        {
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            int size = jsonObject.getAsJsonObject("petriNet").get("size").getAsInt();
            JsonArray matrix = jsonObject.getAsJsonObject("petriNet").getAsJsonArray("matrix");

            int asd = matrix.get(0).getAsInt();

            System.out.println(size);
            System.out.print(asd);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



