package Presentation;

import Business.Character;
import Business.CharacterManager;

import Business.MonsterManager;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Controller {

    private Menu menu = new Menu();
    private MonsterManager monsterManager;
    private CharacterManager characterManager;
    public void run() {
        monsterManager = new MonsterManager();
        characterManager = new CharacterManager();
        int optionListCharacter = 0;
        ArrayList<Integer> positions = new ArrayList<>();
        int selection = 0;

        menu.welcomeMenu();

        try {
            monsterManager.setMonsters(monsterManager.getMonsterDAO().readMonstersFile());
            characterManager.setCharacters(characterManager.getCharactersDAO().readCharactersFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (monsterManager.getMonsters().size() < 0) {
            menu.printMessage("Error: The monsters.json file can’t be accessed.");
        } else {
            
            while(selection != 5) {
                selection = menu.globalMenuSelection();
                switch (selection) {
                    case 1:
                        Character newCharacter = menu.askForCharacterInfo(characterManager.getCharacters());
                        if (newCharacter == null) {
                            break;
                        } else {
                            characterManager.createCharacter(newCharacter);
                            System.out.println("\nThe character " + newCharacter.getName()+" has been created.\n");
                            break;
                        }
                    case 2:
                        if (characterManager.getCharacters().size() > 0) {
                            positions = menu.listCharacters(characterManager.getCharacters());
                            optionListCharacter = menu.optionListCharacters(positions);
                            if (optionListCharacter == 0) {
                                break;
                            } else {
                                menu.showCharacterDetails(positions, characterManager.getCharacters(), optionListCharacter);
                            }
                        } else {
                            System.out.println("No characters created yet.\n");
                        }
                }
            }

        }


        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());



    }
}
