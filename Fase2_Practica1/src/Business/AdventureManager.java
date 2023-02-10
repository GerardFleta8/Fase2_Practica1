package Business;

import Persistance.AdventuresDataInterface;
import Persistance.JSON.AdventuresJsonDAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
     * Returns the list of Adventures stored in the adventureManager
     * @return ArrayList of adventures
     */
    /*public ArrayList<Adventure> getAdventures(){
        return adventures;
    }*/

    /**
     * Sets the adventures to AdventureManager
     * @param adventures ArrayList containing adventures
     */
    /*public void setAdventures(ArrayList<Adventure> adventures) {
        this.adventures = adventures;
    }*/

    /**
     * Gets the adventuresJsonDAO from adventure manager
     * @return instance of class adventuresJsonDAO that adventureManager has stored in order to access its functionalities
     */
    /*
    public AdventuresJsonDAO getAdventuresDAO() {
        return adventuresJsonDAO;
    }*/

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
