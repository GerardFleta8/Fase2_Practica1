package Business;

import Persistance.AdventuresDataInterface;
import Persistance.JSON.AdventuresJsonDAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Manager class for adventures
 */
public class AdventureManager {
    AdventuresDataInterface adventuresDataInterface;
    //private AdventuresJsonDAO adventuresJsonDAO;
    //private ArrayList<Adventure> adventures = new ArrayList<>();

    /**
     * Constructor for AdventureManager, creates a new adventuresJsonDAO to be able
     * to store info about the adventure in JSON
     */
    public AdventureManager(AdventuresDataInterface adventuresDataInterface) {
        this.adventuresDataInterface = adventuresDataInterface;
        //adventuresJsonDAO = new AdventuresJsonDAO();
    }

    /**
     * Adds an adventure to the list of adventures being created
     * @param adventure to add to the list of adventures currently stores
     */
    public void createAdventure(Adventure adventure) {
        ArrayList<Adventure> currentAdventures = new ArrayList<>();
        try {
            currentAdventures = this.adventuresDataInterface.readAdventuresFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        currentAdventures.add(adventure);
        this.adventuresDataInterface.updateAdventuresFile(currentAdventures);
    }


    /**
     * Method that checks whether an adventure has been created.
     * @return Boolean which indicates if it is empty (true) or not (false)
     */
    public boolean isEmpty(){
        ArrayList<Adventure> currentAdventures = new ArrayList<>();
        try {
            currentAdventures = this.adventuresDataInterface.readAdventuresFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(currentAdventures.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * Method which checks if an adventure has already been created with that name.
     * @param name String which we want to verify it is taken or not.
     * @return Boolean that lets us know whether the name is already taken (true) or not.
     */
    public boolean checkAdventureNameTaken(String name){
        ArrayList<Adventure> currentAdventures = new ArrayList<>();
        try {
            currentAdventures = this.adventuresDataInterface.readAdventuresFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(Adventure adventure : currentAdventures){
            if(adventure.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
       return false;
    }

    /**
     * Method which shows the names of the current adventures.
     * @return ArrayList<String> Returns an arrayList containing all of the adventure names as strings.
     */
    public ArrayList<String> showAdventureNames(){
        ArrayList<Adventure> currentAdventures = new ArrayList<>();
        ArrayList<String> strings = new ArrayList<>();
        try {
            currentAdventures = this.adventuresDataInterface.readAdventuresFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for(Adventure a : currentAdventures){
            strings.add(a.getName());
        }
        return strings;
    }

    /**
     * Method which returns the encounters found in a specific Adventure
     * @param position int with the position of the adventure we want to get its encounters.
     * @return ArrayList<Encounter> arraylist with the encounters in a specific adventure.
     */
    public ArrayList<Encounter> getAdventureEncounters(int position){
        ArrayList<Adventure> currentAdventures = new ArrayList<>();
        try {
            currentAdventures = this.adventuresDataInterface.readAdventuresFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for(Adventure a : currentAdventures){
            if(i == position){
                return a.getEncounters();
            }
            i++;
        }
        return null;
    }
}
