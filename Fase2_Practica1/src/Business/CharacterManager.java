package Business;

import Persistance.CharactersJsonDAO;

import java.util.ArrayList;

public class CharacterManager {
    private CharactersJsonDAO charactersJsonDAO;
    private ArrayList<Character> characters = new ArrayList<>();

    public CharacterManager() {
        charactersJsonDAO = new CharactersJsonDAO();
    }
    public ArrayList<Character> getCharacters(){
        return characters;
    }

    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }
    public CharactersJsonDAO getCharactersDAO() {
        return charactersJsonDAO;
    }
}
