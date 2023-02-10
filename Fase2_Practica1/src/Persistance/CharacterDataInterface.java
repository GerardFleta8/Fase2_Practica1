package Persistance;

import Business.Characters.Character;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface CharacterDataInterface {

    ArrayList<Character> readCharactersFile() throws FileNotFoundException;

    void updateCharactersFile(ArrayList<Character> characters);
}
