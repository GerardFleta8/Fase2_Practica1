package Business;

import java.util.ArrayList;

public class Adventure {
    private String name;
    private int numEncounters;
    private ArrayList<Encounter> encounters;

    /**
     * Constructor method for Adventure
     * @param name Adventure name
     * @param numEncounters Number of encounters/fights in the adventure
     * @param encounters ArrayList containing all the encounters
     */
    public Adventure(String name, int numEncounters, ArrayList<Encounter> encounters) {
        this.name = name;
        this.numEncounters = numEncounters;
        this.encounters = encounters;
    }

    /**
     *Gets the Adventure's name
     * @return String with the Adventure's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the number of encounters/fights in an adventure
     * @return Integer with the number of encounters
     */
    public int getNumCombats() {
        return numEncounters;
    }

    /**
     * Gets an adventure's encounters
     * @return ArrayList with the adventure's encounters
     */
    public ArrayList<Encounter> getEncounters() {return encounters;}
}
