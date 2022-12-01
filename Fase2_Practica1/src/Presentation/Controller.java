package Presentation;

import Business.*;
import Business.Character;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Controller {

    private Menu menu = new Menu();
    private MonsterManager monsterManager;
    private AdventureManager adventureManager;
    private CharacterManager characterManager;
    public void run() {
        monsterManager = new MonsterManager();
        characterManager = new CharacterManager();
        adventureManager = new AdventureManager();
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
                            System.out.println("\nThe character " + newCharacter.getName() + " has been created.\n");
                            characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
                            break;
                        }
                    case 2:
                        if (characterManager.getCharacters().size() > 0) {
                            positions = menu.listCharacters(characterManager.getCharacters());
                            optionListCharacter = menu.optionListCharacters(positions);
                            if (optionListCharacter == 0) {
                                System.out.println("");
                                break;
                            } else {
                                String charToDelete = menu.showCharacterDetails(positions, characterManager.getCharacters(), optionListCharacter);
                                if (charToDelete == "") {
                                    break;
                                } else {
                                    boolean deleted = characterManager.deleteCharacter(positions, optionListCharacter - 1, charToDelete);
                                    if (!deleted) {
                                        System.out.println("Incorrect character name, the character won't be deleted.\n");
                                    } else {
                                        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
                                        menu.printMessage("Tavern keeper: \"I'm sorry Kiddo, but you have to leave.\"");
                                        menu.printMessage("");
                                        menu.printMessage("Character " + charToDelete + " left the Guild.");
                                        menu.printMessage("");
                                    }
                                }
                            }
                        } else {
                            System.out.println("No characters created yet.\n");
                        }
                        break;
                    case 3:
                        //ir haciendo
                        //menu.askForAdventureInfo(adventureManager.getAdventures(), monsterManager.getMonsters());
                        //pido la info desde el controler para poder guardar la aventura, si la pido desde el menu
                        //tengo que devolver una adventure para poder guardarlo pero el menu no deberia saber nada
                        String name;
                        int numCombats;
                        menu.printMessage("Tavern keeper: “Planning an adventure? Good luck with that!”\n");
                        name = menu.askForInput("-> Name your adventure: ");
                        boolean nameAlreadyTaken = false;
                        for (Adventure adventure : adventureManager.getAdventures()) {
                            if (adventure.getName().equalsIgnoreCase(name)) {
                                nameAlreadyTaken = true;
                            }
                        }
                        if (nameAlreadyTaken) {
                            System.out.println("Adventure name already taken! Try again.\n");
                            break;
                        }
                        menu.printMessage("Tavern keeper: “You plan to undertake " + name + ", really?”");
                        menu.printMessage("“How long will that take?”\n");
                        numCombats = Integer.parseInt(menu.askForInput("-> How many encounters do you want [1..4]: "));
                        menu.printMessage("Tavern keeper: “" + numCombats + " encounters? That is too much for me...”\n");
                        //int numCombatsAux = numCombats - (numCombats-1);
                        ArrayList<Monster> monstersAlreadyIn = new ArrayList<>();
                        ArrayList<Integer> countMonster = new ArrayList<>();
                        int numCombatsAux = 1;
                        int monsterAdd = 0;
                        int j = 0;
                        while (numCombatsAux < numCombats) {
                            int countBoss = 0;
                            menu.printMessage("* Encounter " + numCombatsAux + " / " + numCombats);
                            menu.printMessage("* Monsters in the Encounter");
                            if (monsterAdd == 0) {
                                menu.printMessage("\t# Empty");
                            }
                            if (monsterAdd != 0) {

                                monstersAlreadyIn.add(monsterManager.getMonsters().get(monsterAdd - 1));
                                for (int i = 1; i <= monstersAlreadyIn.size(); i++) {
                                    if (monstersAlreadyIn.get(i-1).getChallenge().equals("Boss")) {
                                        countBoss++;
                                    }
                                    if (countBoss > 1) {
                                        System.out.println("\nToo much bosses in the adventure!");
                                        break;
                                    }
                                    System.out.printf("\t" +i + ". " + monstersAlreadyIn.get(i - 1).getName()+"\n");
                                }
                            }
                            System.out.println("");
                            int monsterOption = menu.MonsterOptions(); //devuelve 1..3
                            if (monsterOption == 1) {
                                ArrayList<Monster> availableMonsters = new ArrayList<>();
                                availableMonsters = this.monsterManager.getMonsters();
                                monsterAdd = menu.askForMonsterToAdd(availableMonsters);
                            } else if (monsterOption == 3) {
                                numCombatsAux++;
                            }
                        }
                }
                        break;
            }
        }

        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
    }
}
