package Parser;

import com.google.gson.JsonObject;

import java.util.ArrayList;

public class ThreadTriggerConfigurator {

    //Vars for Thread triggers
    private int ntriggers;
    private ArrayList<ThreadTrigger> threadTriggers;


    /**
     * Clase destinada a configurar los triggers de los threads
     * @param threadTriggers recibe todos los triggers que existen en el archivo json
     */
    public ThreadTriggerConfigurator(JsonObject threadTriggers){

        this.ntriggers = threadTriggers.size();

        //leemos todos los triggers del json y los guaradmos en un arraylist de jsonobjects
        ArrayList<JsonObject> triggersJsonObjects = new ArrayList<JsonObject>();
        for(int i = 0; i < ntriggers; i++)
            triggersJsonObjects.add(threadTriggers.getAsJsonObject("T" + i));


        //Al launcher le pasaremos un array list de disparadores
        ArrayList<ThreadTrigger> triggersList = new ArrayList<ThreadTrigger>();

        //a cada trigger ahora lo tenemos que mapear a un ThreadTrigger, a eso lo hacemos de la siguiente manera:
        //creamos un threadtrigger temporal

        for(int j = 0; j < ntriggers; j++) {
            ThreadTrigger tmp = new ThreadTrigger();
            //averiguamos el largo del vector de secuencia de disparos del trigger
            int sequence_size = triggersJsonObjects.get(j).getAsJsonArray("sequence").size();
            int[] vector_tmp = new int[sequence_size];
            //creamos un vector temporal para agregar al thread trigger temporal
            for (int k = 0; k < sequence_size; k++) {
                vector_tmp[k] = triggersJsonObjects.get(j).getAsJsonArray("sequence").get(k).getAsInt();
                System.out.printf("TRIGGER NUMERO %d -- ELEMENTO LEIDO: %d\n", j, vector_tmp[k]);
            }
            //agregamos el vector creado y la cantidad de disparos al thread trigger temporal
            tmp.setNshoots(triggersJsonObjects.get(j).get("nt").getAsInt());
            tmp.setSequence(vector_tmp);

            //por ultimo agregamos el trigger temporal al arreglo de triggers
            triggersList.add(tmp);
        }

        for(int m = 0; m <2; m++){
            for (int l = 0; l < 3; l++) {
                int [] asd = triggersList.get(m).getSequence();
                System.out.printf("EL ELEMENTO %d DEL VECTOR %d es: %d\n", l, m, asd[l]);
            }
        }

        //por ultimo, llamamos al setter de la clase
        this.setThreadTriggers(triggersList);

    }

    public void setThreadTriggers(ArrayList<ThreadTrigger> threadTriggers) {
        this.threadTriggers = threadTriggers;
    }

    public ArrayList<ThreadTrigger> getThreadTriggers() {
        return threadTriggers;
    }
}
