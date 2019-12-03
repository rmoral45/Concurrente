package Config;

import Politica.PoliticMode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class PolicyConfigurator {

    private int next;
    private int [] priorities;
    private PoliticMode mode;

    public PolicyConfigurator(JsonObject policy) {

        JsonArray tmp_priorities = policy.get("priorities").getAsJsonArray();
        this.priorities = new int[tmp_priorities.size()];
        for(int i = 0; i < tmp_priorities.size(); i++)
            this.priorities[i] = tmp_priorities.get(i).getAsInt();

        this.next = policy.get("next").getAsInt();

        String tmp_mode = policy.get("mode").getAsString();
        this.mode = PoliticMode.valueOf(tmp_mode);

    }

    public int getNext() {
        return next;
    }

    public int[] getPriorities() {
        return priorities;
    }

    public PoliticMode getMode() {
        return mode;
    }
}
