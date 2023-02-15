package Presentation;

import Business.*;
import Business.Characters.Character;
import Business.Exceptions.ConexException;
import Business.Monsters.Monster;
import Persistance.API.AdventuresApiDAO;
import Persistance.API.ApiHelper;
import Persistance.API.CharactersApiDAO;
import Persistance.API.MonstersApiDAO;
import Persistance.AdventuresDataInterface;
import Persistance.CharacterDataInterface;
import Persistance.JSON.AdventuresJsonDAO;
import Persistance.JSON.CharactersJsonDAO;
import Persistance.JSON.MonstersJsonDAO;
import Persistance.MonsterDataInterface;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.*;

/**
 * Contoller of the program
 */
public class Controller {

    private Menu menu = new Menu();
    private MonsterManager monsterManager;
    private AdventureManager adventureManager;
    private CharacterManager characterManager;
    /**
     * Runnable of the controller that manages the Menu and Managers
     */
    public void run() {
        CharacterDataInterface CDI = null;
        MonsterDataInterface MDI = null;
        AdventuresDataInterface ADI = null;
        menu.welcomeMenu();
        int dataSource = menu.askForInt("Do you want to use your local or cloud data?\n\t1) Local data\n\t2) Cloud data\n\n-> Answer: ", 1, 2);
        //ApiHelper apiHelper = null;
        //int rip = 0;
        if (dataSource == 2){
            try {
                ADI = new AdventuresApiDAO();
                CDI = new CharactersApiDAO();
                MDI = new MonstersApiDAO();
            } catch (IOException e) {
                CDI = new CharactersJsonDAO();
                MDI = new MonstersJsonDAO();
                ADI = new AdventuresJsonDAO();
            }

        }

        if(dataSource == 1){
            CDI = new CharactersJsonDAO();
            MDI = new MonstersJsonDAO();
            ADI = new AdventuresJsonDAO();
        }

        monsterManager = new MonsterManager(MDI);
        characterManager = new CharacterManager(CDI);
        adventureManager = new AdventureManager(ADI);
        int optionListCharacter = 0;
        ArrayList<Integer> positions = new ArrayList<>();
        int selection = 0;

        /*try {
            //monsterManager.setMonsters(monsterManager.getMonsterDAO().readMonstersFile());
            //characterManager.setCharacters(characterManager.getCharactersDAO().readCharactersFile());
            //adventureManager.setAdventures(adventureManager.getAdventuresDAO().readAdventuresFile());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        if(monsterManager.monsterFileEmpty()){
            menu.printMessage("Error: The monsters.json file can’t be accessed.");
        } else {
            while(selection != 5) {
                //le pasamos si hay mas de 3 jugadores o no
                selection = menu.globalMenuSelection(characterManager.moreThan3Characters());
                switch (selection) {
                    case 1:
                        //Character newCharacter = menu.askForCharacterInfo(characterManager.getCharacters());
                        Character newCharacter;
                        String name = null;
                        String player;
                        int xp;
                        int level;
                        String classType = "";
                        boolean xpCorrect = false;
                        boolean correctName = false;
                        boolean nameAlreadyTaken;
                        int body, mind, spirit, diceTotal;
                        ArrayList<String> xpType = new ArrayList<>();
                        ArrayList<ArrayList> aList = new ArrayList<>();
                        menu.printMessage("Tavern keeper: “Oh, so you are new to this land.”");
                        menu.printMessage("“What’s your name?”");
                        name = menu.askForInput("-> Enter your name: ");
                        menu.printMessage("");
                        if (name.isEmpty()) {
                            System.out.println("You haven't entered any name!\n");
                        } else {
                            nameAlreadyTaken = characterManager.checkNameAlreadyTaken(name);
                            if (nameAlreadyTaken) {
                                menu.printMessage("Name already taken!\n");
                            } else {
                                if (characterManager.checkIfNameHasNumber(name)) {
                                    menu.printMessage("The name entered contains number/s!\n");
                                } else {
                                    if (characterManager.checkSpecialCharacter(name)) {
                                        System.out.println("The name entered contains special characters!\n");
                                    } else {
                                        if (!characterManager.checkFirstCapital(name) || characterManager.hasCapitalLetters(name)) {
                                            name = characterManager.correctionOfFormat(name);
                                            correctName = true;
                                        } else {
                                            correctName = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (correctName) {
                            menu.printMessage("Tavern keeper: “Hello, " +name + ", be welcome.”");
                            menu.printMessage("“And now, if I may break the fourth wall, who is your Player?”\n");
                            player = menu.askForInput("-> Enter the player’s name: ");
                            menu.printMessage("");
                            System.out.println("Tavern keeper: “I see, I see...”");
                            System.out.println("“Now, are you an experienced adventurer?”");
                            level = menu.askForInt("Enter the character's level [1..10]: ", 1, 10);
                            xp = (level-1) * 100;
                            /*while(!xpCorrect) {
                                if (xp > 10 || xp < 1) {
                                    xp = Integer.parseInt(this.askForInput("Invalid level, try one between [1..10]: "));
                                    xpCorrect = false;
                                } else{
                                    xpCorrect = true;
                                }
                            }*/
                            System.out.println("Tavern keeper: “Oh, so you are level " + level+"!”");
                            System.out.println("“Great, let me get a closer look at you...”\n");
                            System.out.println("Generating your stats...\n");
                            for (int i = 0; i < 3; i++) {
                                ArrayList<Integer> dices = new ArrayList<>();
                                dices.add(menu.rollDice(6));
                                dices.add(menu.rollDice(6));
                                dices.add(dices.get(0) + dices.get(1));
                                aList.add(dices);
                            }
                            xpType.add("Body");
                            xpType.add("Mind");
                            xpType.add("Spirit");
                            for (int i = 0; i < aList.size(); i++) {
                                menu.printMessage(xpType.get(i) + ":  You rolled " + aList.get(i).get(2)+ " ("+aList.get(i).get(0)+
                                        " and " + aList.get(i).get(1)+ ").");

                            }
                            menu.printMessage("\nYour stats are:");
                            ArrayList<Integer> totalresult = new ArrayList<>();
                            for (int i = 0; i < aList.size(); i++) {

                                if ((int) aList.get(i).get(2) == 2) {
                                    menu.printMessage("- "+ xpType.get(i)+": -1");
                                    totalresult.add(-1);
                                }
                                if ((int) aList.get(i).get(2) <= 5 && (int) aList.get(i).get(2) >= 3) {
                                    menu.printMessage("- "+ xpType.get(i)+ ": +0");
                                    totalresult.add(0);
                                }
                                if ((int) aList.get(i).get(2) <= 9 && (int) aList.get(i).get(2) >= 6) {
                                    menu.printMessage("- "+ xpType.get(i)+ ": +1");
                                    totalresult.add(1);
                                }
                                if ((int) aList.get(i).get(2) <= 11 && (int) aList.get(i).get(2) >= 10) {
                                    menu.printMessage("- "+ xpType.get(i)+ ": +2");
                                    totalresult.add(2);
                                }
                                if ((int) aList.get(i).get(2) == 12) {
                                    menu.printMessage("- "+ xpType.get(i)+ ": +3");
                                    totalresult.add(3);
                                }
                            }
                            body = totalresult.get(0);
                            mind = totalresult.get(1);
                            spirit = totalresult.get(2);

                            menu.printMessage("\nTavern Keeper: \"Looking good!\"\n\"And, lastly, ?\"");
                            while(true) {
                                classType = menu.askForInput("-> Enter the character's initial class [Adventurer, Cleric, Mage]: ");
                                if (classType.equalsIgnoreCase("Adventurer") || classType.equalsIgnoreCase("Cleric") || classType.equalsIgnoreCase("Mage")){
                                    break;
                                }
                            }
                            newCharacter = new Character(name, player, xp, body, mind, spirit, classType);
                        } else {
                            newCharacter = null;
                        }

                        if (newCharacter == null) {
                            break;
                        } else {
                            characterManager.createCharacter(newCharacter);
                            String updatedClassType = characterManager.showCharacterClassType(name);
                            menu.printMessage("Tavern keeper: \"Any decent party needs one of those.\"\n\"I guess that means you're a "+updatedClassType+" by now, nice!\"\n");
                            menu.printMessage("The character " + newCharacter.getName() + " has been created.\n");

                        }
                        break;
                    case 2:
                        if (characterManager.isEmpty() == false) {

                            menu.printMessage("Tavern keeper: “Lads! They want to see you!”\n“Who piques your interest?”\n");
                            boolean userFoundFlag = false;
                            boolean userFound = false;
                            String playerToFind;
                            ArrayList<String> charStrings = new ArrayList<>();
                            while(!userFoundFlag) {
                                playerToFind = menu.askForInput("-> Enter the name of the Player to filter: ");
                                userFound = characterManager.playerFound(playerToFind);

                                if (!userFound && playerToFind.equals("")) {
                                    positions = new ArrayList<>();

                                    menu.printMessage("You watch as all adventurers get up from their chairs and approach you.\n");

                                    charStrings = characterManager.listCharacters();
                                    int i = 1;
                                    for(String s: charStrings){
                                        menu.printMessage("\t" + i + ". "+s);
                                        positions.add(i);
                                        i++;
                                    }
                                    menu.printMessage("\n\t0. Back\n");
                                    userFoundFlag = true;
                                }
                                if (!userFound && !playerToFind.equals("")) {
                                    System.out.println("Player not found! try again.\n");
                                    userFoundFlag = false;
                                }
                                if(userFound){
                                    userFoundFlag = true;
                                    menu.printMessage("You watch as some adventurers get up from their chairs and approach you.\n");
                                    charStrings = characterManager.playerCharacters(playerToFind);
                                    int i = 1;
                                    for(String s : charStrings){
                                        menu.printMessage("\t" + i + ". "+s);
                                        i++;
                                    }
                                    menu.printMessage("\n\t0. Back\n");
                                }
                            }
                            optionListCharacter = menu.optionListCharacters(charStrings.size());

                            if (optionListCharacter == 0) {
                                menu.printMessage("");
                                break;
                            } else {
                                String charToDelete;
                                String charNameShowDetails = charStrings.get(optionListCharacter-1);
                                menu.printMessage(characterManager.showDetailsOfCharacter(charNameShowDetails));
                                charToDelete = menu.askForInput("\n[Enter name to delete, or press enter to cancel]\nDo you want to delete "+ charNameShowDetails +"?");

                                //menu.printMessage(characterManager.getCharacters().get(positions.get(optionListCharacter-1)).characterDetails());
                                //charToDelete = menu.showCharacterDetails(positions, characterManager.getCharacters(), optionListCharacter);
                                if (charToDelete == "") {
                                    break;
                                } else {
                                    if (!charToDelete.equalsIgnoreCase(charNameShowDetails)) {
                                        menu.printMessage("Incorrect character name, the character won't be deleted.\n");
                                    } else {
                                        characterManager.deleteCharacter(charToDelete);
                                        menu.printMessage("Tavern keeper: \"I'm sorry Kiddo, but you have to leave.\"");
                                        menu.printMessage("");
                                        menu.printMessage("Character " + charToDelete + " left the Guild.");
                                        menu.printMessage("");
                                    }
                                }
                            }
                        } else {
                            menu.printMessage("No characters created yet.\n");
                        }
                        break;
                    //new adventure
                    case 3:
                        String nameAdv;
                        int numCombats = 0;
                        boolean nameCorrect = false;
                        menu.printMessage("Tavern keeper: “Planning an adventure? Good luck with that!”\n");
                        nameAdv = menu.askForInput("-> Name your adventure: ");
                        boolean adNameAlreadyTaken = false;
                        if(!(adventureManager.isEmpty())) {
                            adNameAlreadyTaken = adventureManager.checkAdventureNameTaken(nameAdv);
                        }

                        if (adNameAlreadyTaken) {
                            menu.printMessage("Adventure name already taken! Try again.\n");
                            break;
                        }
                        while (!nameCorrect) {
                            if (nameAdv.equals("")) {
                                nameCorrect = false;
                            } else {
                                nameCorrect = true;
                            }
                        }

                        menu.printMessage("Tavern keeper: “You plan to undertake " + nameAdv + ", really?”\n“How long will that take?”\n");
                        boolean isCorrect = false;
                        int countIncorrect = 0;
                        while (!isCorrect && countIncorrect < 3) {
                            numCombats = Integer.parseInt(menu.askForInput("-> How many encounters do you want [1..4]: "));
                            if (numCombats < 1 || numCombats > 4) {
                                menu.printMessage("Incorrect number of encounters!\n");
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
                                        if (monsterManager.checkMonsterIsBoss(monsterAdd - 1)) {
                                            bossCounter++;
                                        }
                                        if (bossCounter > 1) {
                                            menu.printMessage("\nToo many bosses!");
                                            bossCounter = 1;

                                        } else {
                                            monstersList.add(monsterManager.getMonsterAtPosition(monsterAdd-1));
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
                                            menu.printMessage("\t" + (i + 1) + ". " + monstersIn.get(i).getName() + " (x" + monstersIn.get(i).getNumMonsters() + ")");
                                        } else {
                                            menu.printMessage("\t" + (i + 1) + ". " + monstersIn.get(i).getName());
                                        }
                                        i++;
                                    }
                                }
                                menu.printMessage("");
                                int monsterOption = menu.MonsterOptions();
                                monsterRemove = 0;
                                if (monsterOption == 1) {
                                    ArrayList<String> monsterStrings = this.monsterManager.showMonsters();
                                    int index = 1;
                                    for(String s : monsterStrings){
                                        menu.printMessage(index+". "+s);
                                        index++;
                                    }
                                    menu.printMessage("");
                                    monsterAdd = menu.askForInt("-> Choose a monster to add [1.." +monsterStrings.size() +"]: ", 1, monsterStrings.size());
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
                        adventureManager.createAdventure(new Adventure(nameAdv, numCombats, encounters));
                        break;

                    //play adventure
                    case 4:
                        menu.printMessage("Tavern keeper: \"So, you are looking to go on an adventure?\"\n\"Where do you fancy going?\"\n\nAvailable adventures: ");
                        int i = 0;
                        //igual aqui hay que cogerlas del json y no directamente las que tenga el manager
                        //las que hay en el manager son las que ha creado nuevas, asi que tendriamos que leer todas del json
                        if (adventureManager.isEmpty()) {
                            menu.printMessage("No adventures created yet!\n");
                            break;
                        } else {
                            ArrayList<String> adventureNames = adventureManager.showAdventureNames();
                            for (String s : adventureNames) {
                                menu.printMessage("\t"+(i+1) + ". " + s);
                                i++;
                            }
                            menu.printMessage("");
                            int option = menu.askForInt("-> Choose an adventure: ", 1, adventureNames.size());
                            menu.printMessage("Tavern keeper: “"+ adventureNames.get(option-1)+
                                    " it is!”\n“And how many people shall join you?”\n");
                            int numChar = menu.askForInt("-> Choose a number of characters [3..5]: ", 3, 5);
                            menu.printMessage("Tavern keeper: “Great, "+numChar+" it is.”");
                            menu.printMessage("“Who among these lads shall join you?”\n\n");
                            menu.printMessage("------------------------------");
                            int numChamps = 0;
                            ArrayList<Character> party = new ArrayList<>();
                            int k = 0;
                            while (numChamps < numChar) {
                                menu.printMessage("Your party ("+(numChamps +" / "+ numChar+"):"));
                                for (int j = 1; j <= numChar ; j++) {
                                    if (!party.isEmpty()) {
                                        if(party.size() > (j-1)) {
                                            menu.printMessage("\t" + j + ". " + party.get(j - 1).getName());
                                        }
                                        else {
                                            menu.printMessage("\t"+j+". Empty");
                                        }
                                    } else {
                                        menu.printMessage("\t"+j+". Empty");
                                    }
                                }
                                menu.printMessage("------------------------------");
                                menu.printMessage("Available characters:");
                                ArrayList<String> charStrings = characterManager.listCharacters();
                                for (int j = 1; j <= charStrings.size(); j++) {
                                    menu.printMessage("\t"+j+". " + charStrings.get(j-1));
                                }
                                int characterChosen = menu.askForInt("-> Choose character "+(numChamps+1)+" in your party: ", 1, charStrings.size());
                                boolean alreadyInParty = false;
                                for (int j = 0; j < numChar; j++) {
                                    if(party.isEmpty()){
                                        alreadyInParty = false;
                                        break;
                                    }
                                    else if(party.size() >= j+1 ) {
                                        if (charStrings.get(characterChosen - 1).equals(party.get(j).getName())) {
                                            alreadyInParty = true;
                                        }
                                    }
                                    else{
                                        break;
                                    }
                                }
                                if (alreadyInParty) {
                                    menu.printMessage("Character already in party!\n");
                                    menu.printMessage("\n------------------------------");
                                } else {
                                    menu.printMessage("\n------------------------------");
                                    party.add(k,characterManager.getCharacterAtPosition(characterChosen-1));
                                    k++;
                                    numChamps++;
                                }
                            }
                            menu.printMessage("Your party ("+(numChar +" / "+ numChar+"):"));
                            for (int j = 1; j <= numChar ; j++) {
                                menu.printMessage("\t"+j+". "+ party.get(j-1).getName());
                            }
                            menu.printMessage("------------------------------\n");
                            menu.printMessage("Tavern keeper: “Great, good luck on your adventure lads!”\n\n");
                            menu.printMessage("The “"+adventureNames.get(option-1)+"” will start soon...\n");

                            ArrayList<Encounter> advEncounters = adventureManager.getAdventureEncounters(option-1);


                            for (int j = 0; j < advEncounters.size(); j++) {
                                menu.printMessage("---------------------");
                                menu.printMessage("Starting encounter " + (j + 1) + ":");

                                for (int m = 0; m < advEncounters.get(j).getEncounterMonsters().size(); m++) {
                                    menu.printMessage("- " + advEncounters.get(j).getEncounterMonsters().get(m).getNumMonsters() + "x " +
                                            advEncounters.get(j).getEncounterMonsters().get(m).getName());
                                }
                                menu.printMessage("---------------------\n\n");
                                menu.printMessage("---------------------");
                                menu.printMessage("*** Preparation stage ***");
                                menu.printMessage("---------------------");
                                int l;
                                for (l = 0; l < party.size(); l++) {
                                        party.get(l).calcAndSetInitiative(menu.rollDice(12));
                                        //party.get(l).calcAndSetLevel(0);
                                        //party.get(l).calcAndSetMaxHP();
                                        if(party.get(l).isAlive()){
                                            menu.printMessage(party.get(l).warmUpAction(party));
                                        }else {
                                            menu.printMessage(party.get(l).getName() + " is unconscious");
                                        }

                                }
                                menu.printMessage("");
                                menu.printMessage("Rolling initiative...");
                                ArrayList<Monster> monstersInEncounter = new ArrayList<>();
                                monstersInEncounter = advEncounters.get(j).getEncounterMonsters();
                                ArrayList<Monster> totalMonstersEncounter = new ArrayList<>();
                                for(Monster c: monstersInEncounter){
                                    for(int y = 0; y < c.getNumMonsters(); y++){
                                        totalMonstersEncounter.add(new Monster(c));
                                    }
                                }

                                for(int y = 0; y < totalMonstersEncounter.size(); y++){
                                    totalMonstersEncounter.get(y).calcAndSetInitiative(menu.rollDice(12));
                                }

                                Collections.sort(party, new Comparator<Character>() {
                                    @Override
                                    public int compare(Character o1, Character o2) {
                                        return Integer.valueOf(o2.getInitiative()).compareTo(o1.getInitiative());
                                    }
                                });

                                Collections.sort(totalMonstersEncounter, new Comparator<Monster>() {
                                    @Override
                                    public int compare(Monster o1, Monster o2) {
                                        return Integer.valueOf(o2.getInitiative()).compareTo(o1.getInitiative());
                                    }
                                });

                                int totalTurns = totalMonstersEncounter.size() + party.size();
                                int counterParty = 0;
                                int counterMonsters = 0;
                                for(int x = 0; x < totalTurns; x++){
                                    if(counterParty < party.size()){
                                        if(counterMonsters >= totalMonstersEncounter.size()){
                                            menu.printMessage("\t- "+party.get(counterParty).getInitiative()+"\t"+party.get(counterParty).getName());
                                            counterParty++;
                                        }
                                        else {
                                            if (party.get(counterParty).getInitiative() > totalMonstersEncounter.get(counterMonsters).getInitiative()) {
                                                menu.printMessage("\t- " + party.get(counterParty).getInitiative() + "\t" + party.get(counterParty).getName());
                                                counterParty++;
                                            } else {
                                                menu.printMessage("\t- " + totalMonstersEncounter.get(counterMonsters).getInitiative() + "\t" + totalMonstersEncounter.get(counterMonsters).getName());
                                                counterMonsters++;
                                            }
                                        }
                                    }
                                    else{
                                        menu.printMessage("\t- "+totalMonstersEncounter.get(counterMonsters).getInitiative()+"\t"+totalMonstersEncounter.get(counterMonsters).getName());
                                        counterMonsters++;
                                    }
                                }
                                menu.printMessage("---------------------");
                                menu.printMessage("*** Combat stage ***");
                                menu.printMessage("---------------------");
                                boolean roundOver = false;
                                int round = 1;
                                while(!roundOver){

                                    menu.printMessage("Round "+round+":");
                                    for(Character c: party){
                                        menu.printMessage(c.displayCurrentHp());
                                    }
                                    counterParty = 0;
                                    counterMonsters = 0;
                                    boolean partyDead = true;
                                    boolean monstersDead = true;
                                    for(int x = 0; x < totalTurns; x++){
                                        partyDead = true;
                                        monstersDead = true;
                                        if(counterParty < party.size()){
                                            if(counterMonsters >= totalMonstersEncounter.size()){
                                                if(party.get(counterParty).isAlive()){
                                                    int d10 = menu.rollDice(10);
                                                    String attackAction = party.get(counterParty).attackAction(d10, party, totalMonstersEncounter);
                                                    if(attackAction != null){ //if null: no hay targets vivos
                                                        menu.printMessage(attackAction);
                                                    }
                                                }
                                                counterParty++;
                                            }
                                            else {
                                                if (party.get(counterParty).getInitiative() > totalMonstersEncounter.get(counterMonsters).getInitiative()){
                                                    if(party.get(counterParty).isAlive()){
                                                        int d10 = menu.rollDice(10);
                                                        String attackAction = party.get(counterParty).attackAction(d10, party, totalMonstersEncounter);
                                                        if(attackAction != null){
                                                            menu.printMessage(attackAction);
                                                        }
                                                    }
                                                    counterParty++;
                                                } else {
                                                    if(totalMonstersEncounter.get(counterMonsters).isAlive()){
                                                        menu.printMessage(totalMonstersEncounter.get(counterMonsters).attackMove(party));
                                                    }
                                                    counterMonsters++;
                                                }
                                            }
                                        }
                                        else{
                                            if(totalMonstersEncounter.get(counterMonsters).isAlive()){
                                                menu.printMessage(totalMonstersEncounter.get(counterMonsters).attackMove(party));
                                            }
                                            counterMonsters++;
                                        }

                                        for(Monster c: totalMonstersEncounter){
                                            if(c.isAlive()){
                                                monstersDead = false;
                                            }
                                            //System.out.println(c.getHitPoints()); //to test monster hp is correct
                                        }
                                        if(monstersDead){
                                            for(Character c: party){
                                                if(c.isAlive()){
                                                    partyDead = false;
                                                }
                                            }
                                            break;
                                        }
                                        for(Character c: party){
                                            if(c.isAlive()){
                                                partyDead = false;
                                            }
                                        }
                                        if(partyDead){
                                            break;
                                        }
                                    }

                                    menu.printMessage("\nEnd of round "+round+".");
                                    round++;
                                    if(monstersDead){
                                        menu.printMessage("All enemies are defeated");
                                        menu.printMessage("");
                                        roundOver = true;
                                    }
                                    if(partyDead){
                                        menu.printMessage("Tavern Keeper: \"Lad, wake up. Yes your party fell unconscious.\"");
                                        menu.printMessage("\"Don't worry, you are safe back at the Tavern.\"");
                                        roundOver = true;
                                    }
                                }
                                boolean gameOver = true;
                                for(Character c: party){
                                    if(c.isAlive()){
                                        gameOver = false;
                                    }
                                }
                                if(gameOver){
                                    break;
                                }
                                menu.printMessage("------------------------");
                                menu.printMessage("*** Short rest stage ***");
                                menu.printMessage("------------------------");
                                int xpToAdd = 0;
                                for(Monster c: totalMonstersEncounter){
                                    xpToAdd = xpToAdd + c.getExperience();
                                }
                                /*
                                for(Character c: party){
                                    int currentLevel = c.getLevel();
                                    c.calcAndSetLevel(xpToAdd);
                                    int newLevel = c.getLevel();
                                    if(newLevel > currentLevel){
                                        c.calcAndSetMaxHP();
                                        menu.printMessage(c.getName()+" gains "+xpToAdd+" xp. "+c.getName()+" levels up. They are now lvl "+c.getLevel()+"!");
                                    }
                                    else{
                                        menu.printMessage(c.getName()+" gains "+xpToAdd+" xp.");
                                    }
                                }
                                */

                                String xpString = characterManager.manageXp(party, xpToAdd);
                                menu.printMessage(xpString);
                                menu.printMessage("");

                                for(Character c: party){
                                    int d8 = menu.rollDice(8);
                                    String string = c.restStageAction(d8, party);
                                    if(string != null) {
                                        menu.printMessage(string);
                                    }
                                }
                                menu.printMessage("");
                                menu.printMessage("");
                            }
                            boolean partyAlive = false;
                            for(Character c : party){
                                if(c.isAlive()){
                                    partyAlive = true;
                                }
                            }
                            if(partyAlive){
                                menu.printMessage("Congratulations, your party completed \""+adventureNames.get(option-1)+ "\"");
                                menu.printMessage("");
                            }
                        }
                        break;
                }
            }
        }
        //characterManager.getCharactersDAO().updateCharactersFile(characterManager.getCharacters());
    }
}