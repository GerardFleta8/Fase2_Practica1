package Persistance;

import Business.Character;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class CharactersJsonDAO {

    private String path = "Files/characters.json";

    public ArrayList<Character> readCharactersFile() throws FileNotFoundException {
        Gson g = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Character character[] = g.fromJson(reader, Character[].class);

        ArrayList<Character> characters = new ArrayList<>();
        for(Character c: character){
            characters.add(c);
        }

        return characters;
    }
}
