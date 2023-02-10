package Persistance.API;

import Business.Characters.*;
import Business.Characters.Character;
import Persistance.CharacterDataInterface;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

public class CharactersApiDAO implements CharacterDataInterface {
    ApiHelper apiHelper;

    {
        try {
            apiHelper = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ArrayList<Character> readCharactersFile() throws FileNotFoundException {
        Gson g = new Gson();
        String response = null;

        try {
            response = apiHelper.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_15/characters");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Character character[] = g.fromJson(response, Character[].class);
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
            d.calcAndSetLevel(0);
            d.calcAndSetMaxHP();
            characters.add(d);
            //might have to change how we calc and set lvl
            d.calcAndSetLevel(0); //Calculates and sets the character's level after reading them based on their xp
        }
        return characters;
    }

    @Override
    public void updateCharactersFile(ArrayList<Character> characters) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            apiHelper.deleteFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_15/characters");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            for (Character c: characters) {
                apiHelper.postToUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_15/characters", gson.toJson(c));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
