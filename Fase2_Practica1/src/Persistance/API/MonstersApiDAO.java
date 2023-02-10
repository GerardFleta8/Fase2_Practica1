package Persistance.API;

import Business.Monsters.Boss;
import Business.Monsters.Monster;
import Persistance.MonsterDataInterface;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MonstersApiDAO implements MonsterDataInterface {
    ApiHelper apiHelper;

    {
        try {
            apiHelper = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public ArrayList<Monster> readMonstersFile() throws FileNotFoundException {
        String response;
        Gson g = new Gson();

        try {
            response = apiHelper.getFromUrl("https://balandrau.salle.url.edu/dpoo/shared/monsters");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Monster monster[] = g.fromJson(response, Monster[].class);
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

    @Override
    public boolean isEmpty() {
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
