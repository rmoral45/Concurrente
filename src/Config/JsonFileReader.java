package Config;
import Config.PetriNetConfigurator;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileReader {

    private PetriNetConfigurator pnConfigurator;
    private Config.ThreadTriggerConfigurator triggerConfigurator;

    /* --------------- FOR TESTING ---------------*/
    //DESCOMENTAR CUANDO SE CORRA EL TEST DE PETRINET CONFIGURATOR
    public JsonObject petriNet_test;
    //DESCOMENTAR CUANDO SE CORRA EL TEST DE THREADTRIGGER CONFIGURATOR
    public JsonObject Triggers_test;

    public JsonFileReader(String fileName) {

        try (FileReader reader = new FileReader(fileName))
        {
            //--------------------------------------------------(petriNet)----------------------------------------------
            //leemos json
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            //leemos la petriNet y creamos el configurator de la misma
            JsonObject petriNet = jsonObject.getAsJsonObject("petriNet");
            PetriNetConfigurator pnConfigurator = new PetriNetConfigurator(petriNet);

            //--------------------------------------------------(Thread Triggers)---------------------------------------
            //leemos json
            JsonObject triggers = jsonObject.getAsJsonObject("Triggers");
            //leemos los triggers y creamos el configurator de los mismos
            Config.ThreadTriggerConfigurator triggerConfigurator = new Config.ThreadTriggerConfigurator(triggers);

            /* --------------- FOR TESTING ---------------*/
            //DESCOMENTAR CUANDO SE CORRA EL TEST DE PETRINET CONFIGURATOR
            this.petriNet_test = jsonObject.getAsJsonObject("petriNet");
            this.Triggers_test = jsonObject.getAsJsonObject("Triggers");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PetriNetConfigurator getPnConfigurator() {
        return pnConfigurator;
    }

    public Config.ThreadTriggerConfigurator getTriggerConfigurator() {
        return triggerConfigurator;
    }
}



