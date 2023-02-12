package Business;

import Business.Characters.Champion;
import Business.Characters.Character;
import Business.Characters.Paladin;
import Business.Characters.Warrior;
import Persistance.API.CharactersApiDAO;
import Persistance.CharacterDataInterface;
import Persistance.JSON.CharactersJsonDAO;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Class which Manages the characters and interaction with these.
 */
public class CharacterManager {
    private CharacterDataInterface characterDataInterface;

    /**
     * Constructor for Character manager, creates a new charactersJsonDAO in character manager
     */
    public CharacterManager(CharacterDataInterface characterDataInterface) {
        this.characterDataInterface = characterDataInterface;
    }

    /**
     * Adds a character to the ArrayList of characters
     * @param character Character object with character to add
     */
    public void createCharacter(Character character) {
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        characters.add(character);
        this.characterDataInterface.updateCharactersFile(characters);


    }


    /**
     * Removes a character based on its name
     * @param charToDelete String with the name of the character we want to delete
     * @return Boolean which indicates whether the character was deleted correctly
     */
    public void deleteCharacter(String charToDelete) {

        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
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
        this.characterDataInterface.updateCharactersFile(characters);
    }

    /**
     * Is used to verify that there are more than 3 characters (in order to enable/disable the option to play adventure)
     * @return Boolean with true for more than 3 characters, and false otherwise.
     */
    public boolean moreThan3Characters(){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(characters.size() >= 3){
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

    /**
     * Method which checks if a character name has already been taken.
     * @param name String with the name we want to check
     * @return boolean indicated whether the name is taken or not.
     */
    public boolean checkNameAlreadyTaken(String name){
        boolean nameTaken = false;
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
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

    /**
     * Method which checks if the character file is empty.
     * @return boolean indicated whether it is empty or not.
     */
    public boolean isEmpty(){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(characters.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Method which helps us list the characters by name
     * @return list containing the names of all the characters.
     */
    public ArrayList<String> listCharacters(){
        ArrayList<String> chars = new ArrayList<>();
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 1;
        for(Character c : characters){
            chars.add(c.getName());
        }

        /*
        //Test API:
        CharacterDataInterface cdi = new CharactersApiDAO();
        cdi.updateCharactersFile(characters);
        try {
            cdi.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //END tests API
        */
        return chars;
    }

    /**
     * Checks if a player has been found given a string
     * @param name String with the player name
     * @return Boolean which indicates whether the player was found or not in the list of characters.
     */
    public boolean playerFound(String name){
        boolean playerFound = false;
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
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


    /**
     * Method to help show all the characters created by a player.
     * @param name name of the player whose characters we want to show
     * @return ArrayList</String> of strings with the player's characters.
     */
    public ArrayList<String> playerCharacters(String name){
        ArrayList<Character> characters = new ArrayList<>();
        ArrayList<String> charStrings = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
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

    /**
     * Method which shows a character's details upon request.
     * @param name String with the given character's name
     * @return String with the character details.
     */
    public String showDetailsOfCharacter(String name){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
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

    /**
     * Method which returns a character at a given position. Is used when playing an adventure and forming a party.
     * @param position int with the position of the character
     * @return Character, returns the character which we want to add to the party.
     */
    public Character getCharacterAtPosition(int position){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = this.characterDataInterface.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        int i = 0;
        for(Character c : characters){
            if(i == position){
                return c;
            }
            i++;
        }
        return null;
    }


    /**
     * Method which returns a character's class type. Is used when dealing damage to indicate the dmg type.
     * @param name String with the character's name.
     * @return String with the character's class type.
     */
    public String showCharacterClassType(String name){
        ArrayList<Character> characters = new ArrayList<>();
        try {
            characters = characterDataInterface.readCharactersFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        for (Character character : characters) {
            if (character.getName().equalsIgnoreCase(name)) {
                return character.getClassType();
            }
        }
        return null;
    }

    /**
     * Method which, at the end of an encounter, manages the xp for the entire party.
     * @param party ArrayList of characters which form the current party which is playing an adventure.
     * @param xp int with the xp we need to add to the members of the party.
     * @return String with the result of adding xp to all members which will be shown on screen by the menu.
     */
    public String manageXp(ArrayList<Character> party, int xp){
        String string = null;
        int i = 0;
        for(Character c : party){
            String aux = c.addXp(xp);
            if(i == 0) {
                string = aux;
            }
            else{
                string = string + aux;
            }
            if (aux.contains("Warrior")){
                c = new Warrior(c);
                party.set(i, c);
                party.get(i).calcAndSetMaxHP();
            } else if (aux.contains("Champion")) {
                c = new Champion(c);
                party.set(i, c);
                party.get(i).calcAndSetMaxHP();
            } else if (aux.contains("Paladin")) {
                c = new Paladin(c);
                party.set(i, c);
                party.get(i).calcAndSetMaxHP();
            }
            i++;
        }

        return string;
    }
}
