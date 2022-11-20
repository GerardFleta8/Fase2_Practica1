package Presentation;

import Business.MonsterManager;

import java.io.FileNotFoundException;

public class Controller {

    private Menu menu = new Menu();
    private MonsterManager monsterManager;
    public void run() {
        monsterManager = new MonsterManager();
        try {
            monsterManager.setMonsters(monsterManager.getMonsterDAO().readMonstersFile());
        } catch (FileNotFoundException ignored) {

        }
        menu.welcomeMenu();
        if (monsterManager.getMonsters().size() < 0) {
            menu.printMessage("Error: The monsters.json file can’t be accessed.");
        } else {
            menu.globalMenuSelection();
        }





    }
}
