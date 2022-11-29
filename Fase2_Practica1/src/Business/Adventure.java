package Business;

import java.util.ArrayList;

public class Adventure {
    private String name;
    private int numCombats;
    private ArrayList<Monster> monsters;

    public Adventure(String name, int numCombats, ArrayList<Monster> monsters) {
        this.name = name;
        this.numCombats = numCombats;
        this.monsters = monsters;
    }

    public String getName() {
        return name;
    }

    public int getNumCombats() {
        return numCombats;
    }

    public ArrayList<Monster> getMonsters() {
        return monsters;
    }
}
