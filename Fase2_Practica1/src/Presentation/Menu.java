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
        private String correctionOfFormat(String name) {
            String nameCorrected;
            nameCorrected= name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();

            return nameCorrected;

        }
        public void askForCharacterInfo(ArrayList<Character> characters) {
            String name = null;
            boolean correctName = false;
            boolean nameAlreadyTaken;

            while(!correctName) {
                System.out.println("Tavern keeper: “Oh, so you are new to this land.”");
                System.out.println("“What’s your name?”");
                name = this.askForInput("-> Enter your name: ");
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
                                if (!checkFirstCapital(name)) {
                                    name = correctionOfFormat(name);
                                }

                                correctName = true;
                            }
                        }
                    }
                }
                //FALTA COMPROBAR NOMBRE
            }

            System.out.println("Tavern keeper: “Hello, " +name + ", be welcome.”");

            //return(new Character());
        }




}
