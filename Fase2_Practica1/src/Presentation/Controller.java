package Presentation;

import Business.Character;
import Business.CharacterManager;

import Business.MonsterManager;

import java.io.FileNotFoundException;

public class Controller {

    private Menu menu = new Menu();
    private MonsterManager monsterManager;
    private CharacterManager characterManager;
    public void run() {
        monsterManager = new MonsterManager();
        characterManager = new CharacterManager();
        int selection;
        try {
            monsterManager.setMonsters(monsterManager.getMonsterDAO().readMonstersFile());
            characterManager.setCharacters(characterManager.getCharactersDAO().readCharactersFile());
        } catch (FileNotFoundException ignored) {

        }
        menu.welcomeMenu();
        if (monsterManager.getMonsters().size() < 0) {
            menu.printMessage("Error: The monsters.json file can’t be accessed.");
        } else {
            selection = menu.globalMenuSelection();
            switch (selection) {
                case 1:
                    /*Character newCharacter*/

                        menu.askForCharacterInfo(characterManager.getCharacters());

                    break;
            }
        }






    }
}
