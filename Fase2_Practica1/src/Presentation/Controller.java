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
            adventureManager.setAdventures(adventureManager.getAdventuresDAO().readAdventuresFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        if (monsterManager.getMonsters().size() < 0) {
            menu.printMessage("Error: The monsters.json file can’t be accessed.");
        } else {
            while(selection != 5) {
                //le pasamos si hay mas de 3 jugadores o no
                selection = menu.globalMenuSelection(characterManager.moreThan3Characters());
                switch (selection) {
                    case 1:
                        Character newCharacter = menu.askForCharacterInfo(characterManager.getCharacters());
                        if (newCharacter == null) {
                            break;
                        } else {
                            characterManager.createCharacter(newCharacter);
                            System.out.println("\nThe character " + newCharacter.getName() + " has been created.\n");
                            characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());

                        }
                        break;
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
                        if(!(adventureManager.getAdventures().isEmpty())) {
                            for (Adventure adventure : adventureManager.getAdventures()) {
                                if (adventure.getName().equalsIgnoreCase(name)) {
                                    nameAlreadyTaken = true;
                                }
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
                        int countIncorrect = 0;
                        while (!isCorrect && countIncorrect < 3) {
                            numCombats = Integer.parseInt(menu.askForInput("-> How many encounters do you want [1..4]: "));
                            if (numCombats < 1 || numCombats > 4) {
                                System.out.println("Incorrect number of encounters!\n");
                                countIncorrect++;

                                isCorrect = false;
                            } else {
                                isCorrect = true;
                            }
                        }

                        if (countIncorrect >2) {
                            break;
                        }
                        ArrayList<Encounter> encounters = new ArrayList<>();
                        menu.printMessage("Tavern keeper: “" + numCombats + " encounters? That is too much for me...”\n");
                        ArrayList<Monster> monstersIn = new ArrayList<>();
                        ArrayList<Monster> monstersList = new ArrayList<>();
                        int numCombatsAux = 1;
                        int bossCounter = 0;
                        while (numCombatsAux <= numCombats) {
                            Set<String> seen = new HashSet<String>();
                            Set<String> seentwice = new HashSet<String>();
                            monstersIn = new ArrayList<>();
                            monstersList = new ArrayList<>();
                            int monsterAdd = 0;
                            int continuar = 0;
                            bossCounter = 0;
                            int monsterRemove = 0;
                            while (continuar == 0) {

                                int j = 0;
                                menu.printMessage("* Encounter " + numCombatsAux + " / " + numCombats);
                                menu.printMessage("* Monsters in the Encounter");
                                if (monsterAdd == 0) {
                                    menu.printMessage("\t# Empty");
                                }
                                if (monsterAdd != 0) {
                                    //si monster remove != significa que queremos borrar
                                    // y hemos actualizado seentwice y no la queremos sobre escribir
                                    if (monsterRemove == 0) {
                                        if (monsterManager.getMonsters().get(monsterAdd - 1).getChallenge().equals("Boss")) {
                                            bossCounter++;
                                        }
                                        if (bossCounter > 1) {
                                            System.out.println("\nToo many bosses!");
                                            bossCounter = 1;

                                        } else {
                                            monstersList.add(monsterManager.getMonsters().get(monsterAdd - 1));
                                        }


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
                                    monsterRemove = menu.askForInt("-> Which monster do you want to delete:", 1, seentwice.size());
                                    int i = 0;
                                    monsterRemove = monsterRemove - 1;
                                    String aux = "";
                                    for (String s : seentwice) {
                                        if (i == monsterRemove) {
                                            aux = s;
                                        }
                                        i++;
                                    }
                                    seentwice.remove(aux); //hay que pasarle el nombre aux (para borrar en un set)
                                    monsterRemove++; //para que no entre al if si han borrado el 1 y monsterRemove es 0 otra vez
                                    monstersIn = new ArrayList<>();


                                    //los hemos borrado del set seentwice, falta borrarlos del monstersList
                                    boolean monsterFound = true;
                                    while (monsterFound == true) {
                                        int found = 0;
                                        i = 0;
                                        monsterFound = false;
                                        for (Monster s : monstersList) {
                                            if (aux.equalsIgnoreCase(s.getName())) {
                                                found = i;
                                                monsterFound = true;
                                            }
                                            i++;
                                        }
                                        if (monsterFound) {
                                            monstersList.remove(found);
                                        }
                                    }
                                    if (monstersList.size() == 0) {
                                        monsterAdd = 0;
                                    }
                                } else if (monsterOption == 3) {
                                    //guardar la current arraylist monstersIn al encuentro y añadir el encuentro
                                    Encounter currentEncounter = new Encounter(monstersIn);
                                    encounters.add(currentEncounter);
                                    continuar = 1;
                                    numCombatsAux++;
                                }
                            }
                        }
                        adventureManager.createAdventure(new Adventure(name, numCombats, encounters));
                        adventureManager.getAdventuresDAO().updateAdventuresFile(adventureManager.getAdventures());
                        //falta guardar la adventure en JSON con json manager
                        break;

                    //play adventure
                    case 4:
                        menu.printMessage("Tavern keeper: \"So, you are looking to go on an adventure?\"\n\"Where do you fancy going?\"");
                        menu.printMessage("");
                        menu.printMessage("Available adventures:");
                        int i = 0;
                        //igual aqui hay que cogerlas del json y no directamente las que tenga el manager
                        //las que hay en el manager son las que ha creado nuevas, asi que tendriamos que leer todas del json
                        if (adventureManager.getAdventures().isEmpty()) {
                            System.out.println("No adventures created yet!\n");
                            break;
                        } else {
                            for (Adventure a : adventureManager.getAdventures()) {
                                menu.printMessage("\t"+(i+1) + ". " + a.getName());
                            }
                            menu.printMessage("");
                            int option = menu.askForInt("-> Choose an adventure: ", 1, adventureManager.getAdventures().size());
                            System.out.println("Tavern keeper: “"+ adventureManager.getAdventures().get(option-1).getName()+" – " +
                                    "The Battle under the Stars it is!” “And how many people shall join you?”\n");
                            int numChar = menu.askForInt("-> Choose a number of characters [3..5]: ", 3, 5);
                            System.out.println("Tavern keeper: “Great, "+numChar+" it is.”");
                            System.out.println("“Who among these lads shall join you?”\n\n");
                            System.out.println("------------------------------");
                            int numChamps = 0;
                            ArrayList<Character> party = new ArrayList<>();
                            for (int j = 0; j < numChar; j++) {
                                Character aux = new Character("", "", 0,0,0,0,"");
                                party.add(aux);

                            }
                            int k = 0;
                            while (numChamps <= numChar) {
                                System.out.println("Your party ("+(numChamps +" / "+ numChar+"):"));

                                for (int j = 1; j <= numChar ; j++) {
                                    if (!party.get(j-1).getName().isEmpty()) {
                                        System.out.println("\t"+j+". "+ party.get(j-1).getName());
                                    } else {
                                        System.out.println("\t"+j+". Empty");
                                    }
                                }

                                System.out.println("------------------------------");
                                System.out.println("Available characters:");
                                for (int j = 1; j <= characterManager.getCharacters().size(); j++) {
                                    System.out.println("\t"+j+". " + characterManager.getCharacters().get(j-1).getName());
                                }
                                int characterChosen = menu.askForInt("-> Choose character "+(numChamps+1)+" in your party: ", 1, characterManager.getCharacters().size());
                                System.out.println("------------------------------\n");
                                party.add(k,characterManager.getCharacters().get(characterChosen-1));
                                k++;
                                numChamps++;

                            }
                        }
                        break;
                }

            }
        }
        characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
    }
}