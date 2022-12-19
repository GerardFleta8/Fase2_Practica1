package Presentation;

import Business.Characters.Character;
import Business.Monster;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
* Menu class
* Creates all menus
*/
public class Menu {

    /**
     * Method that creates the first messages to display
     */
    public void welcomeMenu() {
        System.out.println("    ____ _               __        __      ____ ____   ___   _____"  );
        System.out.println("   / __/(_)__ _   ____  / /___    / /     / __// _  \\ / _ \\ / ___/");
        System.out.println(" __\\ \\ / //  | \\ / _ \\ / // -_)  / /___ __\\ \\ / ,  _// ___// (_ /");
        System.out.println("/____//_//_/_/_// ___//_/ \\__/  /_____//____//_/\\ _\\/_/    \\___/");
        System.out.println("               /_/ ");
        System.out.println("\nWelcome to Simple LSRPG.\n");
        System.out.println("Loading data...\n");
    }
    /**
     * Mètode que demana a l'usuari quin rol vol tenir
     * @param message print message from menu
     */
    public void printMessage(String message) {
        System.out.println(message);
    }
    /**
     * Creates the global menu to display
     * @return option selected
     */
    public int globalMenuSelection(boolean moreThan3Char) {
        Scanner scanner = new Scanner(System.in);
        String input;
        int selection = 0;
        boolean optionIsCorrect = false;
        if(moreThan3Char) {
            System.out.println("The tavern keeper looks at you and says:");
            System.out.println("“Welcome adventurer! How can I help you?”");
            System.out.println("\n\t1) Character creation\n\t2) List characters\n\t3) Create an adventure\n\t" +
                    "4) Start an adventure\n\t5) Exit\n");
            System.out.println("Your answer: ");
        }
        else{
            System.out.println("The tavern keeper looks at you and says:");
            System.out.println("“Welcome adventurer! How can I help you?”");
            System.out.println("\n\t1) Character creation\n\t2) List characters\n\t3) Create an adventure\n\t" +
                    "4) Start an adventure (disabled: create 3 characters first)\n\t5) Exit\n");
            System.out.println("Your answer: ");

        }
        while(!optionIsCorrect) {
            input = scanner.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")|| input.equals("4")&&moreThan3Char  ||
                    input.equals("5")) {
                optionIsCorrect = true;
                switch(input) {
                    case "1":
                        selection = 1;
                        break;
                    case "2":
                        selection = 2;
                        break;
                    case "3":
                        selection = 3;
                        break;
                    case "4":
                        selection = 4;
                        break;
                    case "5":
                        selection = 5;
                        break;
                }
            } else {
                if(moreThan3Char) {
                    System.out.println("Incorrect option, please choose between one of the following:\n");
                    System.out.println("\n\t1) Character creation\n\t2) List characters\n\t3) Create an adventure\n\t" +
                            "4) Start an adventure\n\t5) Exit\n");
                    System.out.println("Your answer: ");
                }
                else{
                    System.out.println("Incorrect option, please choose between one of the following:\n");
                    System.out.println("\n\t1) Character creation\n\t2) List characters\n\t3) Create an adventure\n\t" +
                            "4) Start an adventure (disabled: create 3 characters first)\n\t5) Exit\n");
                    System.out.println("Your answer: ");
                }
            }
        }
        return selection;
    }
    /**
     * Method that asks the user for an input of type string
     * @return String wrote by user
     * @param message message to display
     */
    public String askForInput (String message) {
        Scanner scan = new Scanner(System.in);

        System.out.println(message);

        return scan.nextLine();
    }
    /**
     * Method that asks the user for an int, typically to choose an option
     * @return Integer with the option selected
     * @param max Max integer to be selected
     * @param message Message to be displayed
     * @param min Min integer to be selected
     */
    public int askForInt(String message, int min, int max){
        Scanner s = new Scanner(System.in);
        int option = 0;
        int incorrectInput = 1;
        do{
            System.out.println(message);
            try{
                option = s.nextInt();
                if(option >= min && option <= max){
                    incorrectInput = 0;
                }else{
                    System.out.println("Please enter a number between "+min+" and "+max);
                }
            }catch(InputMismatchException e){
                System.out.println("Please enter a number.");
            }finally{
                s.nextLine();
            }
        }while(incorrectInput == 1);
        return option;
    }

    /**
     * Method that rolls dice
     * @return value of the dice
     * @param numFaces Number of faces for the dice to roll
     */
    public int rollDice(int numFaces) {
        int dice;
        dice = (int) (Math.random()*numFaces + 1);

        return dice;
    }

    /**
     * Method that asks for the info of the character to create
     * @return new character
     * @param characters list of characters created
     */
    /*public Character askForCharacterInfo(ArrayList<Character> characters) {
        String name = null;
        String player;
        int xp;
        String classType;
        boolean xpCorrect = false;
        boolean correctName = false;
        boolean nameAlreadyTaken;
        int body, mind, spirit, diceTotal;
        ArrayList<String> xpType = new ArrayList<>();
        ArrayList<ArrayList> aList = new ArrayList<>();
        System.out.println("Tavern keeper: “Oh, so you are new to this land.”");
        System.out.println("“What’s your name?”");
        name = this.askForInput("-> Enter your name: ");

        System.out.println("");
        if (name.isEmpty()) {
            System.out.println("You haven't entered any name!\n");
        } else {
            nameAlreadyTaken = false;
            if (!characters.isEmpty()) {
                for (Character character: characters) {
                    if (character.getName().equalsIgnoreCase(name)) {
                        nameAlreadyTaken = true;
                    }
                }
            }
            if (nameAlreadyTaken) {
                System.out.println("Name already taken!\n");
            } else {
                if (checkIfNameHasNumber(name)) {
                    System.out.println("The name entered contains number/s!\n");
                } else {
                    if (checkSpecialCharacter(name)) {
                        System.out.println("The name entered contains special characters!\n");
                    } else {
                        if (!checkFirstCapital(name) || hasCapitalLetters(name)) {
                            name = correctionOfFormat(name);
                            correctName = true;
                        } else {
                            correctName = true;
                        }
                    }
                }
            }
        }
        if (correctName) {
            System.out.println("Tavern keeper: “Hello, " +name + ", be welcome.”");
            System.out.println("“And now, if I may break the fourth wall, who is your Player?”\n");
            player = this.askForInput("-> Enter the player’s name: ");
            System.out.println("");
            System.out.println("Tavern keeper: “I see, I see...”");
            System.out.println("“Now, are you an experienced adventurer?”");
            xp = Integer.parseInt(this.askForInput("Enter the character's level [1..10]: "));
            while(!xpCorrect) {
                if (xp > 10 || xp < 1) {
                    xp = Integer.parseInt(this.askForInput("Invalid level, try one between [1..10]: "));
                    xpCorrect = false;
                } else{
                    xpCorrect = true;
                }
            }
            System.out.println("Tavern keeper: “Oh, so you are level " + xp+"!”");
            System.out.println("“Great, let me get a closer look at you...”\n");
            System.out.println("Generating your stats...\n");
            for (int i = 0; i < 3; i++) {
                ArrayList<Integer> dices = new ArrayList<>();
                dices.add(rollDice(6));
                dices.add(rollDice(6));
                dices.add(dices.get(0) + dices.get(1));
                aList.add(dices);
            }
            xpType.add("Body");
            xpType.add("Mind");
            xpType.add("Spirit");
            for (int i = 0; i < aList.size(); i++) {
                System.out.println(xpType.get(i) + ":  You rolled " + aList.get(i).get(2)+ " ("+aList.get(i).get(0)+
                        " and " + aList.get(i).get(1)+ ").");

            }
            System.out.println("\nYour stats are:");
            ArrayList<Integer> totalresult = new ArrayList<>();
            for (int i = 0; i < aList.size(); i++) {

                if ((int) aList.get(i).get(2) == 2) {
                    System.out.println("- "+ xpType.get(i)+": -1");
                    totalresult.add(-1);
                }
                if ((int) aList.get(i).get(2) <= 5 && (int) aList.get(i).get(2) >= 3) {
                    System.out.println("- "+ xpType.get(i)+ ": +0");
                    totalresult.add(0);
                }
                if ((int) aList.get(i).get(2) <= 9 && (int) aList.get(i).get(2) >= 6) {
                    System.out.println("- "+ xpType.get(i)+ ": +1");
                    totalresult.add(1);
                }
                if ((int) aList.get(i).get(2) <= 11 && (int) aList.get(i).get(2) >= 10) {
                    System.out.println("- "+ xpType.get(i)+ ": +2");
                    totalresult.add(2);
                }
                if ((int) aList.get(i).get(2) == 12) {
                    System.out.println("- "+ xpType.get(i)+ ": +3");
                    totalresult.add(3);
                }
            }
            body = totalresult.get(0);
            mind = totalresult.get(1);
            spirit = totalresult.get(2);
            classType = "Adventurer";
            return(new Character(name, player, xp, body, mind, spirit, classType));
        } else {
            return null;
        }

    }
    */
    /**
     * Method that lists all the characters created
     * @return list of positions to show
     * @param characters list of all characters
     */
    public ArrayList<Integer> listCharacters(ArrayList<Character> characters) {
        int option, i = 0;
        ArrayList<String> charactersNames = new ArrayList<>();
        boolean userFoundFlag = false;
        boolean userFound = false;
        ArrayList<Integer> positions = new ArrayList<>();
        String playerToFind;
        System.out.println("Tavern keeper: “Lads! They want to see you!”");
        System.out.println("“Who piques your interest?”\n");
        while(!userFoundFlag) {
            playerToFind = this.askForInput("-> Enter the name of the Player to filter: ");
            for (int j = 0; j < characters.size() ; j++) {
                if (characters.get(j).getPlayer().equalsIgnoreCase(playerToFind)) {
                    userFound = true;
                }
            }

            if (!userFound && playerToFind.equals("")) {
                positions = new ArrayList<>();

                System.out.println("You watch as all adventurers get up from their chairs and approach you.\n");
                for (i = 1; i <= characters.size() ; i++) {
                    System.out.println("\t" + i + ". "+ characters.get(i-1).getName());
                    positions.add(i);
                }
                System.out.println();
                System.out.println("\t0. Back\n");
                userFoundFlag = true;
            }
            if (!userFound && !playerToFind.equals("")) {
                System.out.println("Player not found! try again.\n");
                userFoundFlag = false;
            }
            if (userFound) {
                positions = new ArrayList<>();
                userFoundFlag = true;
                System.out.println("You watch as some adventurers get up from their chairs and approach you.\n");
                for (int j = 0; j < characters.size() ; j++) {
                    if (characters.get(j).getPlayer().equalsIgnoreCase(playerToFind)) {
                        charactersNames.add(characters.get(j).getName());
                        positions.add(j);
                    }
                }

                for (int j = 1; j <= charactersNames.size(); j++) {
                    System.out.println("\t" + j + ". "+ charactersNames.get(j-1));
                }
                System.out.println();
                System.out.println("\t0. Back\n");

            }


        }
        return positions;


    }

    /**
     * Method that shows the details of the character chosen
     * @return name to be deleted
     * @param positions positions to be shown
     * @param characters list of characters
     * @param option option of the character shown
     */
    public String showCharacterDetails(ArrayList<Integer> positions, ArrayList<Character> characters, int option) {
        System.out.println("Tavern keeper: “Hey " + characters.get(positions.get(option-1)).getName()+ " get here; the" +
                    " boss wants to see you!”");
        System.out.println("* Name:   "+ characters.get(positions.get(option-1)).getName());
        System.out.println("* Player: "+ characters.get(positions.get(option-1)).getPlayer());
        System.out.println("* Class:  "+ characters.get(positions.get(option-1)).getClassType());
        System.out.println("* XP:     "+ characters.get(positions.get(option-1)).getXp());
        if (characters.get(positions.get(option-1)).getBody() >= 0) {
            System.out.println("* Body:   +"+ characters.get(positions.get(option-1)).getBody());
        }
        if(characters.get(positions.get(option-1)).getBody() < 0) {
            System.out.println("* Body:   -"+ characters.get(positions.get(option-1)).getBody());
        }
        if (characters.get(positions.get(option-1)).getMind() >= 0) {
            System.out.println("* Mind:   +"+ characters.get(positions.get(option-1)).getMind());
        }
        if(characters.get(positions.get(option-1)).getMind() < 0) {
            System.out.println("* Mind:   -"+ characters.get(positions.get(option-1)).getMind());
        }
        if (characters.get(positions.get(option-1)).getSpirit() >= 0) {
            System.out.println("* Spirit: +"+ characters.get(positions.get(option-1)).getSpirit());
        }
        if(characters.get(positions.get(option-1)).getSpirit() < 0) {
            System.out.println("* Spirit: "+ characters.get(positions.get(option-1)).getSpirit());
        }
        System.out.println();

        System.out.println("[Enter name to delete, or press enter to cancel]");

        return this.askForInput("Do you want to delete "+characters.get(positions.get(option-1)).getName()+"?");
    }

    /**
     * Method that chooses the character to meet
     * @return option of the character chosen
     * @param positions positions of characters for each player
     */
    public int optionListCharacters(ArrayList<Integer> positions) {
        int option = 0;
        option = Integer.parseInt(this.askForInput("Who would you like to meet [0.."+positions.size() + "]: "));
        return option;
    }
    /**
     * Method that chooses the option to create, delete (monsters) or continue the encounter
     * @return option chosen
     */
    public int MonsterOptions() {
        System.out.println();
        System.out.println("1. Add monster");
        System.out.println("2. Remove monster");
        System.out.println("3. Continue");
        System.out.println();
        int option = 0;
        boolean isCorrect = false;
        while (!isCorrect) {
            option = Integer.parseInt(this.askForInput("-> Enter an option [1..3]: "));
            if(option < 1 || option > 3) {
                System.out.println("Incorrect option!\n");
                isCorrect = false;
            } else {
                isCorrect = true;
            }
        }
        System.out.println("");
        return option;
    }
    /**
     * Method that asks which monster to add to the encounter
     * @return option chosen
     * @param monsters list of all monsters to be chosen
     */
    public int askForMonsterToAdd(ArrayList<Monster> monsters) {
        int i = 1;
        for(Monster c : monsters){
            System.out.println(i+". "+c.getName()+"("+c.getChallenge()+")");
            i++;
        }
        System.out.println();
        int option = 0;
        boolean isCorrect = false;
        while (!isCorrect) {
            option = Integer.parseInt(this.askForInput("-> Choose a monster to add [1.." +monsters.size() +"]: "));
            if(option < 1 || option > monsters.size()) {
                System.out.println("Monster not in list!\n");
                isCorrect = false;
            } else {
                isCorrect = true;
            }
        }
        return option;
    }
    }
