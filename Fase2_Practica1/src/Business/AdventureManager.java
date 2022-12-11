package Business;

import Persistance.AdventuresJsonDAO;
import Persistance.CharactersJsonDAO;

import java.util.ArrayList;

public class AdventureManager {
    private AdventuresJsonDAO adventuresJsonDAO;
    private ArrayList<Adventure> adventures = new ArrayList<>();

    /**
     * Constructor for AdventureManager, creates a new adventuresJsonDAO to be able
     * to store info about the adventure in JSON
     */
    public AdventureManager() {
        adventuresJsonDAO = new AdventuresJsonDAO();
    }

    /**
     * Adds an adventure to the list of adventures being created
     * @param adventure to add to the list of adventures currently stores
     */
    public void createAdventure(Adventure adventure) {
        adventures.add(adventure);
    }

    /**
     * Returns the list of Adventures stored in the adventureManager
     * @return ArrayList of adventures
     */
    public ArrayList<Adventure> getAdventures(){
        return adventures;
    }

    /**
     * Sets the adventures to AdventureManager
     * @param adventures ArrayList containing adventures
     */
    public void setAdventures(ArrayList<Adventure> adventures) {
        this.adventures = adventures;
    }

    /**
     * Gets the adventuresJsonDAO from adventure manager
     * @return instance of class adventuresJsonDAO that adventureManager has stored in order to access its functionalities
     */
    public AdventuresJsonDAO getAdventuresDAO() {
        return adventuresJsonDAO;
    }
}
