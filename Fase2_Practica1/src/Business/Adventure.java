package Business;

import java.util.ArrayList;

public class Adventure {
    private String name;
    private int numEncounters;
    //private ArrayList<Monster> monsters;
    private ArrayList<Encounter> encounters;

    public Adventure(String name, int numEncounters, ArrayList<Encounter> encounters) {
        this.name = name;
        this.numEncounters = numEncounters;
        //this.monsters = monsters;
        this.encounters = encounters;
    }

    public String getName() {
        return name;
    }

    public int getNumCombats() {
        return numEncounters;
    }

    public ArrayList<Encounter> getEncounters() {return encounters;}
}
