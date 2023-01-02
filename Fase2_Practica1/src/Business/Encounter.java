package Business;

import Business.Monsters.Monster;

import java.util.ArrayList;
/**
 * Encounter class
 */
public class Encounter {
    private ArrayList<Monster> monstersInEncounter;
    /**
     * Constructor for encounters
     * @param monsters list of all monsters
     */
    public Encounter(ArrayList<Monster> monsters){
        monstersInEncounter = monsters;
    }
    /**
     * Method that gets the monsters in the encounter
     * @return list of monsters in the encounter
     */
    public ArrayList<Monster> getEncounterMonsters(){
        return this.monstersInEncounter;
    }
}
