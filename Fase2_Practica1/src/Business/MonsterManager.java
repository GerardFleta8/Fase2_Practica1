package Business;

import Persistance.MonstersJsonDAO;

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
}
