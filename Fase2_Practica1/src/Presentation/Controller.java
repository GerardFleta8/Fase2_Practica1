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
                            characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());

                            break;
                        }
                    case 2:
                        if (characterManager.getCharacters().size() > 0) {
                            positions = menu.listCharacters(characterManager.getCharacters());
                            optionListCharacter = menu.optionListCharacters(positions);
                            if (optionListCharacter == 0) {
                                break;
                            } else {
                                String charToDelete = menu.showCharacterDetails(positions, characterManager.getCharacters(), optionListCharacter);
                                if(charToDelete == ""){
                                    break;
                                }
                                else{
                                    boolean deleted = characterManager.deleteCharacter(positions, optionListCharacter - 1, charToDelete);

                                    if (!deleted) {
                                        System.out.println("Incorrect character name, the character won't be deleted.\n");
                                    } else {
                                        for (int i = 0; i < characterManager.getCharacters().size(); i++) {
                                            System.out.println(characterManager.getCharacters().get(i).getName());

                                        }
                                        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
                                        menu.printMessage("Tavern keeper: \"I'm sorry Kiddo, but you have to leave.\"");
                                        menu.printMessage("");
                                        menu.printMessage("Character "+charToDelete+" left the Guild.");
                                        menu.printMessage("");
                                    }
                                }
                            }
                        } else {
                            System.out.println("No characters created yet.\n");
                        }
                        break;
                }
            }

        }


        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());



    }
}
