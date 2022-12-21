package Business;

import Persistance.MonstersJsonDAO;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
/**
 * Manages monsters
 */
public class MonsterManager {
    private ArrayList<Monster> monsters = new ArrayList<>();
    private MonstersJsonDAO monstersJsonDAO;
    /**
     * Constructor for MonsterManager
     */
    public MonsterManager () {
        monstersJsonDAO = new MonstersJsonDAO();
    }
    /**
     * Method that gets all monsters
     * @return list of monsters
     */
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
    /**
     * Method that sets monsters to the manager
     * @param monsters list of monsters
     */
    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }
    /**
     * Method that gets the DAO of monsters
     * @return MonstersJsonDAO
     */
    public MonstersJsonDAO getMonsterDAO() {
        return monstersJsonDAO;
    }

    public boolean checkMonsterIsBoss(int position){
        ArrayList<Monster> monsters = new ArrayList<>();
        try {
            monsters = monstersJsonDAO.readMonstersFile();
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
            monsters = monstersJsonDAO.readMonstersFile();
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
            monsters = monstersJsonDAO.readMonstersFile();
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
}
