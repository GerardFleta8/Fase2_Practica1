package Business;

import java.util.ArrayList;

public class Adventure {
    private String name;
    private int numCombats;
    //private ArrayList<Monster> monsters;
    private ArrayList<Encounter> encounters;

    public Adventure(String name, int numCombats, ArrayList<Encounter> encounters) {
        this.name = name;
        this.numCombats = numCombats;
        //this.monsters = monsters;
        this.encounters = encounters;
    }

    public String getName() {
        return name;
    }

    public int getNumCombats() {
        return numCombats;
    }

    /*public ArrayList<Monster> getMonsters() {
        return monsters;
    }*/
}
