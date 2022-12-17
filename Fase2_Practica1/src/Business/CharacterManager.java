package Business;

import Business.Characters.Character;
import Persistance.CharactersJsonDAO;

import java.util.ArrayList;

public class CharacterManager {
    private CharactersJsonDAO charactersJsonDAO;
    private ArrayList<Character> characters = new ArrayList<>();

    /**
     * Constructor for Character manager, creates a new charactersJsonDAO in character manager
     */
    public CharacterManager() {
        charactersJsonDAO = new CharactersJsonDAO();
    }

    /**
     * Adds a character to the ArrayList of characters
     * @param character Character object with character to add
     */
    public void createCharacter(Character character) {
        characters.add(character);
    }

    /**
     * Gets the arrayList containing the characters in characterManager
     * @return ArrayList of characters
     */
    public ArrayList<Character> getCharacters(){
        return characters;
    }

    /**
     * Gets the charactersJsonDAO from characterManager
     * @return CharacterJsonDAO object
     */
    public CharactersJsonDAO getCharactersDAO() {
        return charactersJsonDAO;
    }

    /**
     * Sets the Characters arrayList in CharacterManager to those inputted to this setter
     * @param characters ArrayList with characters to set
     */
    public void setCharacters(ArrayList<Character> characters) {
        this.characters = characters;
    }

    /**
     * Removes a character based on its name
     * @param positions Arraylist of integers with the different characters a player has
     * @param characterIndex Integer with the position of the character we want to delete
     * @param charToDelete String with the name of the character we want to delete
     * @return Boolean which indicates whether the character was deleted correctly
     */
    public boolean deleteCharacter(ArrayList<Integer> positions, int characterIndex, String charToDelete) {

        if (charToDelete.equals(characters.get(positions.get(characterIndex)).getName())) {
            characters.remove(characters.get(positions.get(characterIndex)));
            return true;
        } else {
            return false;
        }
    }

    /**
     * Is used to verify that there are more than 3 characters (in order to enable/disable the option to play adventure)
     * @return Boolean with true for more than 3 characters, and false otherwise.
     */
    public boolean moreThan3Characters(){
        if(this.characters.size() >= 3){
            return true;
        }
        return false;
    }
}
