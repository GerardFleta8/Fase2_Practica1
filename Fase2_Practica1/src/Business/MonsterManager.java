package Business;

import Business.Monsters.Monster;
import Persistance.JSON.MonstersJsonDAO;
import Persistance.MonsterDataInterface;

import java.io.FileNotFoundException;
import java.util.ArrayList;
/**
 * Manages monsters
 */
public class MonsterManager {
    private MonsterDataInterface monsterDataInterface;
    //private ArrayList<Monster> monsters = new ArrayList<>();
    //private MonstersJsonDAO monstersJsonDAO;
    /**
     * Constructor for MonsterManager
     */
    public MonsterManager (MonsterDataInterface monsterDataInterface) {
        this.monsterDataInterface = monsterDataInterface;
        //monstersJsonDAO = new MonstersJsonDAO();
    }


    /**
     * Method which checks if a given monster has 'Boss' type.
     * @param position int with the position of the monster we wish to check.
     * @return Boolean indicated whether the monster in question is a boss or not.
     */
    public boolean checkMonsterIsBoss(int position){
        ArrayList<Monster> monsters = new ArrayList<>();
        try {
            monsters = monsterDataInterface.readMonstersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for(Monster m : monsters){
            if(i == position ){
                return m.getChallenge().equalsIgnoreCase("Boss");
            }
            i++;
        }
        return false;
    }

    public ArrayList<String> showMonsters(){
        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        try {
            monsters = monsterDataInterface.readMonstersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(Monster m : monsters){
            strings.add(m.monsterNameAndClass());
        }
        return strings;
    }

    public Monster getMonsterAtPosition(int position){
        ArrayList<Monster> monsters = new ArrayList<>();
        try {
            monsters = monsterDataInterface.readMonstersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for(Monster m : monsters){
            if(i == position ){
                return m;
            }
            i++;
        }
        return null;
    }

    /**
     * Method which checks if the monster file is empty or not
     * @return Boolean which indicates whether the file is empty (true) or not (false)
     */
    public boolean monsterFileEmpty(){
        return this.monsterDataInterface.isEmpty();
    }
}
