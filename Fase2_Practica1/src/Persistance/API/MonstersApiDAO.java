package Persistance.API;

import Business.Monsters.Boss;
import Business.Monsters.Monster;
import Persistance.MonsterDataInterface;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class incharge of interaction with Monsters in API
 */
public class MonstersApiDAO implements MonsterDataInterface {
    ApiHelper apiHelper;

    /*public MonstersApiDAO(ApiHelper apiHelper){
        this.apiHelper = apiHelper;
    }*/
    public MonstersApiDAO() throws IOException {
        apiHelper = new ApiHelper();
    }

    /**
     * Method in charge of reading the monsters from API
     * @return list containing the monsters in API
     * @throws FileNotFoundException
     */
    @Override
    public ArrayList<Monster> readMonstersFile() throws IOException, FileNotFoundException {
        String response;
        Gson g = new Gson();


        response = apiHelper.getFromUrl("https://balandrau.salle.url.edu/dpoo/shared/monsters");

        Monster monster[] = g.fromJson(response, Monster[].class);
        ArrayList<Monster> monsters = new ArrayList<>();
        for(Monster m: monster){
            if(m.getChallenge().equalsIgnoreCase("Boss")){
                monsters.add(new Boss(m));
            }
            else {
                monsters.add(m);
            }
        }
        return monsters;
    }

    /**
     * Method which checks if there are monsters in API
     * @return Boolean indicated whether there are monsters or not.
     */
    @Override
    public boolean isEmpty() throws IOException {
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
