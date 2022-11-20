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
            System.out.println("\n\t1) Character creaton\n\t2) List characters\n\t3) Create an adventure\n\t" +
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
                    System.out.println("\n\t1) Character creaton\n\t2) List characters\n\t3) Create an adventure\n\t" +
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
        public Character askForCharacterInfo(ArrayList<Character> characters) {
            String name;
            boolean correctName = false;
            boolean nameAlreadyTaken = false;
            Scanner scanner = new Scanner(System.in);
            System.out.println("Tavern keeper: “Oh, so you are new to this land.”");
            System.out.println("“What’s your name?”");
            name = this.askForInput("-> Enter your name: ");
            while(!correctName) {
                if (name.isEmpty()) {
                    name = this.askForInput("Name can't be empty, try again: ");
                } else {
                    nameAlreadyTaken = false;
                    for (Character character: characters) {
                        if (character.getName().equals(name)) {
                            nameAlreadyTaken = true;
                            break;
                        }
                    }
                    if (nameAlreadyTaken) {
                        name = this.askForInput("Name is already taken, try a different one: ");
                    } else {
                        correctName = true;
                    }
                }
            }

            return(new Character());
        }
}
