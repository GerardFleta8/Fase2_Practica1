package Presentation;

import Business.*;
import Business.Character;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
                    //new adventure
                    case 3:
                        String name;
                        int numCombats = 0;
                        boolean nameCorrect = false;
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
                        while (!nameCorrect) {
                            if (name.equals("")) {
                                nameCorrect = false;
                            } else {
                                nameCorrect = true;
                            }
                        }
                        menu.printMessage("Tavern keeper: “You plan to undertake " + name + ", really?”");
                        menu.printMessage("“How long will that take?”\n");
                        boolean isCorrect = false;
                        while (!isCorrect) {
                            numCombats = Integer.parseInt(menu.askForInput("-> How many encounters do you want [1..4]: "));
                            if(numCombats < 1 || numCombats > 4) {
                                System.out.println("Incorrect option!\n");
                                isCorrect = false;
                            } else {
                                isCorrect = true;
                            }
                        }
                        menu.printMessage("Tavern keeper: “" + numCombats + " encounters? That is too much for me...”\n");
                        ArrayList<Monster> monstersIn = new ArrayList<>();
                        ArrayList<Monster> monstersList = new ArrayList<>();
                        int numCombatsAux = 1;
                        while (numCombatsAux <= numCombats) {
                            Set<String> seen = new HashSet<String>();
                            Set<String> seentwice = new HashSet<String>();
                            monstersIn = new ArrayList<>();
                            monstersList = new ArrayList<>();
                            int monsterAdd = 0;
                            int continuar = 0;
                            int monsterRemove = 0;
                            while(continuar == 0) {
                                int countBoss = 0;
                                int j = 0;
                                menu.printMessage("* Encounter " + numCombatsAux + " / " + numCombats);
                                menu.printMessage("* Monsters in the Encounter");
                                if (monsterAdd == 0) {
                                    menu.printMessage("\t# Empty");
                                }
                                if (monsterAdd != 0) {
                                    //si monster remove != significa que queremos borrar
                                    // y hemos actualizado seentwice y no la queremos sobre escribir
                                    if(monsterRemove == 0) {
                                        monstersList.add(monsterManager.getMonsters().get(monsterAdd - 1));
                                        for (Monster s : monstersList) {
                                            seentwice.add(s.getName());
                                        }
                                    }
                                    int flag = 0;
                                    int i = 0;
                                    for (String s : seentwice) {
                                        int auxNum = 0;
                                        int k = 0;
                                        for (Monster n : monstersList) {
                                            if (s.equalsIgnoreCase(n.getName())) {
                                                flag = k;
                                                auxNum++;
                                            }
                                            k++;
                                        }
                                        monstersIn.add(monstersList.get(flag));
                                        monstersIn.get(i).setNumMonsters(auxNum);
                                        if (monstersIn.get(i).getNumMonsters() > 1) {
                                            System.out.printf("\t" + (i + 1) + ". " + monstersIn.get(i).getName() + " (x" + monstersIn.get(i).getNumMonsters() + ")\n");
                                        } else {
                                            System.out.printf("\t" + (i + 1) + ". " + monstersIn.get(i).getName() + "\n");
                                        }
                                        i++;
                                    }
                                }
                                System.out.println("");
                                int monsterOption = menu.MonsterOptions();
                                monsterRemove = 0;
                                if (monsterOption == 1) {
                                    ArrayList<Monster> availableMonsters = new ArrayList<>();
                                    availableMonsters = this.monsterManager.getMonsters();
                                    monsterAdd = menu.askForMonsterToAdd(availableMonsters);
                                    monstersIn = new ArrayList<>();
                                } else if (monsterOption == 2) {
                                    monsterRemove = menu.askForInt("-> Which monster do you want to delete:",1, seentwice.size());
                                    int i = 0;
                                    monsterRemove = monsterRemove - 1;
                                    String aux = "";
                                    for(String s: seentwice){
                                        if(i == monsterRemove){
                                            aux = s;
                                        }
                                        i++;
                                    }
                                    seentwice.remove(aux); //hay que pasarle el nombre aux (para borrar en un set)
                                    monsterRemove++; //para que no entre al if si han borrado el 1, y monster Remove es 0 otra vez
                                    monstersIn = new ArrayList<>();
                                    i = 0;
                                    //los hemos borrado del set seentwice, falta borrarlos del monstersList
                                    for(Monster s : monstersList){
                                        if(aux.equalsIgnoreCase(s.getName())){
                                            monstersList.remove(i);
                                        }
                                        i++;
                                    }
                                } else if (monsterOption == 3) {
                                    //guardar la current arraylist monstersIn
                                    continuar = 1;
                                    numCombatsAux++;
                                }
                            }
                        }
                }
                break;
            }
        }
        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
    }
}