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
/**
 * DAO of characters
 */
public class CharactersJsonDAO {

    private String path = "characters.json";
    /**
     * Method that reads the characters file
     * @return list of characters in the json file
     * @throws FileNotFoundException
     */
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
    /**
     * Method that updates the characters file
     * @param characters list of characters to write in the file
     */
    public void updateCharactersFile(ArrayList<Character> characters) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try(FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(characters, fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
