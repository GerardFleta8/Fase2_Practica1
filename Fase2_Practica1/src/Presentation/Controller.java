package Presentation;

import Business.MonsterManager;

public class Controller {

    private Menu menu = new Menu();
    private MonsterManager monsterManager;
    public void run() {

        menu.welcomeMenu();
        if (monsterManager.getMonsters().size() < 0) {
            menu.printMessage("Error: The monsters.json file can’t be accessed.");
        } else {
            menu.globalMenuSelection();
        }





    }
}
