package Business;

import Business.Monsters.Monster;
import Persistance.JSON.MonstersJsonDAO;
import Persistance.MonsterDataInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Manages monsters
 */
public class MonsterManager {
    private MonsterDataInterface monsterDataInterface;

    /**
     * Constructor for MonsterManager
     */
    public MonsterManager (MonsterDataInterface monsterDataInterface) {
        this.monsterDataInterface = monsterDataInterface;
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for(Monster m : monsters){
            if(i == position ){
                return m.isBoss();
            }
            i++;
        }
        return false;
    }

    /**
     * Method which gets all the monsters' name and class
     * @return List of strings with the name and class of each monster
     */
    public ArrayList<String> showMonsters(){
        ArrayList<Monster> monsters = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        try {
            monsters = monsterDataInterface.readMonstersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for(Monster m : monsters){
            strings.add(m.monsterNameAndClass());
        }
        return strings;
    }

    /**
     * Method which gets the monster at a given position
     * @param position int indicated the position of the monster
     * @return Monster at given position.
     */
    public Monster getMonsterAtPosition(int position){
        ArrayList<Monster> monsters = new ArrayList<>();
        try {
            monsters = monsterDataInterface.readMonstersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
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
    public boolean monsterFileEmpty() throws IOException {
        boolean empty;
            empty = this.monsterDataInterface.isEmpty();
        return empty;
    }
}
