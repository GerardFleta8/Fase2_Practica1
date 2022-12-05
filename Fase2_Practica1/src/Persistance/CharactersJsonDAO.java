package Persistance;

import Business.Character;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CharactersJsonDAO {

    private String path = "characters.json";

    public ArrayList<Character> readCharactersFile() throws FileNotFoundException {
        Gson g = new Gson();
        JsonReader reader = new JsonReader(new FileReader(path));
        Character character[] = g.fromJson(reader, Character[].class);

        ArrayList<Character> characters = new ArrayList<>();
        if (character == null) {
            return new ArrayList<>();
        }
        for(Character c: character){
            characters.add(c);
        }


        return characters;
    }

    public void updateCharactersFile(ArrayList<Character> characters) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(characters, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
