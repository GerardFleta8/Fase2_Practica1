package Persistance;

import Business.Adventure;
import Business.Character;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class AdventuresJsonDAO {

    private String path = "adventures.json";

    public void updateAdventuresFile(ArrayList<Adventure> adventures) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try(FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(adventures, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
