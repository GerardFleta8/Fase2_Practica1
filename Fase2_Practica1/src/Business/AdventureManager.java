package Business;

import Persistance.AdventuresJsonDAO;

import java.util.ArrayList;

public class AdventureManager {
    private AdventuresJsonDAO adventuresJsonDAO;
    private ArrayList<Adventure> adventures = new ArrayList<>();

    public AdventureManager() {
        adventuresJsonDAO = new AdventuresJsonDAO();
    }
    public void createAdventure(Adventure adventure) {
        adventures.add(adventure);
    }
    public ArrayList<Adventure> getAdventures(){
        return adventures;
    }
    public void setAdventures(ArrayList<Adventure> adventures) {
        this.adventures = adventures;
    }
}
