package Business;

import java.util.ArrayList;

public class Encounter {
    private ArrayList<Monster> monstersInEncounter;

    public Encounter(ArrayList<Monster> monsters){
        monstersInEncounter = monsters;
    }
    public ArrayList<Monster> getEncounterMonsters(){
        return this.monstersInEncounter;
    }
}
