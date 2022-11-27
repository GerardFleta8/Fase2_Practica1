package Presentation;

import Business.Character;


import java.util.ArrayList;
import java.util.Scanner;

public class Menu {


        public void welcomeMenu() {
            System.out.println("Welcome to Simple LSRPG.\n");
            System.out.println("Loading data...\n");
        }

        public void printMessage(String message) {
            System.out.println(message);
        }

        public int globalMenuSelection() {
            Scanner scanner = new Scanner(System.in);
            String input;
            int selection = 0;
            boolean optionIsCorrect = false;
            System.out.println("The tavern keeper looks at you and says:");
            System.out.println("“Welcome adventurer! How can I help you?”");
            System.out.println("\n\t1) Character creation\n\t2) List characters\n\t3) Create an adventure\n\t" +
                    "4) Start an adventure\n\t5) Exit\n");
            System.out.println("Your answer: ");
            while(!optionIsCorrect) {
                input = scanner.nextLine();
                if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") ||
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
                    System.out.println("Incorrect option, please choose between one of the following:\n");
                    System.out.println("\n\t1) Character creation\n\t2) List characters\n\t3) Create an adventure\n\t" +
                            "4) Start an adventure\n\t5) Exit\n");
                    System.out.println("Your answer: ");
                }
            }
            return selection;
        }

        public String askForInput (String message) {
            Scanner scan = new Scanner(System.in);

            System.out.println(message);

            return scan.nextLine();
        }
        public boolean checkIfNameHasNumber(String name) {
            if (name.matches(".*[0-9].*")) {
                return true;
            } else{
                return false;
            }


        }
        public boolean checkSpecialCharacter(String name) {
            String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
            boolean hasSpecialCharacter = false;
            for (int i=0; i < name.length() ; i++)
            {
                char ch = name.charAt(i);
                if(specialCharactersString.contains(java.lang.Character.toString(ch))) {
                    hasSpecialCharacter = true;

                }
                else if(i == name.length()-1)
                   hasSpecialCharacter = false;
            }
            return hasSpecialCharacter;

        }

        public boolean checkFirstCapital(String name) {
            boolean hasCapital = false;

            if (java.lang.Character.isUpperCase(name.charAt(0))) {
                hasCapital = true;
            } else {
                hasCapital = false;
            }
            return hasCapital;

        }

        public boolean hasCapitalLetters(String name) {
            for (int i = 0; i < name.length(); i++) {
                if(java.lang.Character.isUpperCase(name.charAt(i + 1))) {
                    return true;
                }
            }
            return false;

        }
        private String correctionOfFormat(String name) {
            String nameCorrected;
            nameCorrected= name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

            return nameCorrected;

        }

        public int rollDice(int numFaces) {
            int dice;
            dice = (int) (Math.random()*numFaces + 1);

            return dice;
        }


        public Character askForCharacterInfo(ArrayList<Character> characters) {
            String name = null;
            String owner;
            int xp;
            boolean xpCorrect = false;
            boolean correctName = false;
            boolean nameAlreadyTaken;
            int body, mind, spirit, diceTotal;
            ArrayList<String> xpType = new ArrayList<>();
            ArrayList<ArrayList> aList = new ArrayList<>();

            while(!correctName) {
                System.out.println("Tavern keeper: “Oh, so you are new to this land.”");
                System.out.println("“What’s your name?”");
                name = this.askForInput("-> Enter your name: ");
                System.out.println("");
                if (name.isEmpty()) {
                    System.out.println("You haven't entered any name!\n");
                    globalMenuSelection();
                } else {
                    nameAlreadyTaken = false;
                    for (Character character: characters) {
                        if (character.getName().equalsIgnoreCase(name)) {
                            nameAlreadyTaken = true;
                        }
                    }
                    if (nameAlreadyTaken) {
                        System.out.println("Name already taken!\n");
                        globalMenuSelection();
                    } else {
                        if (checkIfNameHasNumber(name)) {
                            System.out.println("The name entered contains number/s!\n");
                            globalMenuSelection();
                        } else {
                            if (checkSpecialCharacter(name)) {
                                System.out.println("The name entered contains special characters!\n");
                                globalMenuSelection();
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
            }
            System.out.println("Tavern keeper: “Hello, " +name + ", be welcome.”");
            System.out.println("“And now, if I may break the fourth wall, who is your Player?”\n");
            owner = this.askForInput("-> Enter the player’s name: ");
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

            return(new Character(name, owner, xp, body, mind, spirit));
        }




}
