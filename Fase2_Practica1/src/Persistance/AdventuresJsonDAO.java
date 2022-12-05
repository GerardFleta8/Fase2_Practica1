package Persistance;

import Business.Adventure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
    public ArrayList<Adventure> readAdventuresFile() throws FileNotFoundException {
        Gson g = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Adventure adventure[] = g.fromJson(reader, Adventure[].class);

        ArrayList<Adventure> adventures = new ArrayList<>();
        if (adventure == null) {
            return new ArrayList<>();
        }
        for(Adventure a: adventure){
            adventures.add(a);
        }


        return adventures;
    }
}
