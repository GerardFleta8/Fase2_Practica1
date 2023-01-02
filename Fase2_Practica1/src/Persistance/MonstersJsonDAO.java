package Persistance;

import Business.Monsters.Boss;
import Business.Monsters.Monster;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
/**
 * DAO for monsters
 */
public class MonstersJsonDAO {

    private String filename = "monsters.json";
    /**
     * Method that reads the monsters file
     * @return list of monsters in the json file
     * @throws FileNotFoundException
     */
    public ArrayList<Monster> readMonstersFile() throws FileNotFoundException {
        Gson g = new Gson();

        JsonReader reader = new JsonReader(new FileReader(filename));
        Monster monster[] = g.fromJson(reader, Monster[].class);
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m: monster){
            if(m.getChallenge().equalsIgnoreCase("Boss")){
                Monster d = new Boss(m);
                monsters.add(d);
            }
            else {
                monsters.add(m);
            }
        }
        return monsters;
    }

    public boolean isEmpty(){
        ArrayList<Monster> monsters;
        try {
            monsters = this.readMonstersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(monsters.isEmpty()){
            return true;
        }
        return false;
    }
}
