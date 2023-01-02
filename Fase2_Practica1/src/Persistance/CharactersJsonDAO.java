package Persistance;

import Business.Characters.*;
import Business.Characters.Character;
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
            c.calcAndSetLevel(0);
            Character d = null;
            if(c.getClassType().equalsIgnoreCase("Adventurer")){
                if(c.getLevel() < 4) {
                    d = new Adventurer(c);
                } else if (c.getLevel() >= 4 && c.getLevel() < 8) {
                    d = new Warrior(c);
                } else if (c.getLevel() >= 8){
                    d = new Champion(c);
                }
            } else if (c.getClassType().equalsIgnoreCase("Warrior")) {
                d = new Warrior(c);
            } else if (c.getClassType().equalsIgnoreCase("Champion")) {
                d = new Champion(c);
            } else if (c.getClassType().equalsIgnoreCase("Cleric")) {
                if(c.getLevel() < 5) {
                    d = new Cleric(c);
                } else if(c.getLevel() >= 5){
                    d = new Paladin(c);
                }
            } else if (c.getClassType().equalsIgnoreCase("Paladin")) {
                d = new Paladin(c);
            } else if (c.getClassType().equalsIgnoreCase("Mage")) {
                d = new Mage(c);
            }
            //Character d = new Mage(c);
            characters.add(d);
            //might have to change how we calc and set lvl
            d.calcAndSetLevel(0); //Calculates and sets the character's level after reading them based on their xp
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
