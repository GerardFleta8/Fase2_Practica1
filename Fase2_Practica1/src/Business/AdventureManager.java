package Business;

import Persistance.AdventuresJsonDAO;
import Persistance.CharactersJsonDAO;

import java.util.ArrayList;

public class AdventureManager {
    private AdventuresJsonDAO adventuresJsonDAO;
    private ArrayList<Adventure> adventures = new ArrayList<>();

    //para guardar la aventura al json, primero tenemos que leer las que hay en el json
    //y luego añadir la nueva y guardarlas todas

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
    public AdventuresJsonDAO getAdventuresDAO() {
        return adventuresJsonDAO;
    }
}
