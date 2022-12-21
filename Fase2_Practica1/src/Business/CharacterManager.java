package Business;

import Business.Characters.Character;
import Persistance.CharactersJsonDAO;

import java.io.FileNotFoundException;
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
    public void deleteCharacter(String charToDelete) {

        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        int found = 0;
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(charToDelete)) {
                found = i;
            }
            i++;
        }
        characters.remove(found);
        this.charactersJsonDAO.updateCharactersFile(characters);
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


    /**
     * Method that checks if the name of the character contains number
     * @return boolean if name has number
     * @param name name of the character to be checked
     */
    public boolean checkIfNameHasNumber(String name) {
        if (name.matches(".*[0-9].*")) {
            return true;
        } else{
            return false;
        }
    }

    /**
     * Method that transforms the name to a correct standard
     * @return String of the name already corrected
     * @param name name of the character to be converted
     */
    public String correctionOfFormat(String name) {
        String nameCorrected;
        nameCorrected= name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        return nameCorrected;
    }
    /**
     * Method that checks if the name has first capital letter
     * @return boolean if name has first capital
     * @param name name of the character to be checked
     */
    public boolean checkFirstCapital(String name) {
        boolean hasCapital = false;

        if (java.lang.Character.isUpperCase(name.charAt(0))) {
            hasCapital = true;
        } else {
            hasCapital = false;
        }
        return hasCapital;

    }

    /**
     * Method that checks if the name of the character contains capital letter
     * @return boolean if name contains capital letters
     * @param name name of the character to be checked
     */
    public boolean hasCapitalLetters(String name) {
        for (int i = 1; i < name.length(); i++) {
            if(java.lang.Character.isUpperCase(name.charAt(i))) {
                return true;
            }
        }
        return false;

    }

    /**
     * Method that checks if the name of the character contains number
     * @return boolean if name has special character
     * @param name name of the character to be checked
     */
    public boolean checkSpecialCharacter(String name) {
        String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        boolean hasSpecialCharacter = false;
        for (int i=0; i < name.length() ; i++)
        {
            char ch = name.charAt(i);
            if(specialCharactersString.contains(java.lang.Character.toString(ch))) {
                hasSpecialCharacter = true;

            }
            else if(i == name.length()-1)
                hasSpecialCharacter = false;
        }
        return hasSpecialCharacter;

    }

    public boolean checkNameAlreadyTaken(String name){
        boolean nameTaken = false;
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!characters.isEmpty()) {
            for (Character character : characters) {
                if (character.getName().equalsIgnoreCase(name)) {
                    nameTaken = true;
                }
            }
        }
        return nameTaken;
    }

    public boolean isEmpty(){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(characters.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String> listCharacters(){
        ArrayList<String> chars = new ArrayList<>();
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 1;
        for(Character c : characters){
            chars.add(c.getName());
        }
        return chars;
    }

    public boolean playerFound(String name){
        boolean playerFound = false;
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if (!characters.isEmpty()) {
            for (Character character : characters) {
                if (character.getPlayer().equalsIgnoreCase(name)) {
                    playerFound = true;
                }
            }
        }
        return playerFound;
    }

    public ArrayList<String> playerCharacters(String name){
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> charStrings = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Character character : characters) {
            if (character.getPlayer().equalsIgnoreCase(name)) {
                charStrings.add(character.getName());
            }
        }
        return charStrings;
    }

    public String showDetailsOfCharacter(String name){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = charactersJsonDAO.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character.characterDetails();
            }
        }
        return null;
    }
}
