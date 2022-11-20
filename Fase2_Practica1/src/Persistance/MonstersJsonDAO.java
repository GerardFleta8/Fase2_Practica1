package Persistance;

import Business.Character;
import Business.Monster;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MonstersJsonDAO {

    private String filename = "Files/monsters.json";
    public ArrayList<Monster> readMonstersFile() throws FileNotFoundException {
        Gson g = new Gson();
        JsonReader reader = new JsonReader(new FileReader(filename));
        Monster monster[] = g.fromJson(reader, Monster[].class);
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m: monster){
            monsters.add(m);
        }

        return monsters;
    }
}
