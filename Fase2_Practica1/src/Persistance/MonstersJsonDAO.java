package Persistance;

import Business.Character;
import Business.Monster;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class MonstersJsonDAO {

    private String path = "Files/monsters.json";
    public ArrayList<Monster> readMonstersFile() throws FileNotFoundException {
        Gson g = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Monster monster[] = g.fromJson(reader, Character[].class);

        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m: monster){
            monsters.add(m);
        }

        return monsters;
    }
}
