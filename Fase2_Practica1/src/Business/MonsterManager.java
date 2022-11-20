package Business;

import Persistance.MonstersJsonDAO;

import java.util.ArrayList;

public class MonsterManager {
    private ArrayList<Monster> monsters = new ArrayList<>();
    private MonstersJsonDAO monstersJsonDAO;

    public MonsterManager () {
        monstersJsonDAO = new MonstersJsonDAO();
    }
    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
    public void setMonsters(ArrayList<Monster> monsters) {
        this.monsters = monsters;
    }

    public MonstersJsonDAO getMonsterDAO() {
        return monstersJsonDAO;
    }
}
