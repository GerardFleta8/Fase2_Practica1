import Business.Characters.Character;
import Persistance.API.CharactersApiDAO;
import Persistance.CharacterDataInterface;
import Presentation.Controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Main method
 * Calls controller
 */
public class Main {
    public static void main(String[] args) {


        Controller controller = new Controller();
        controller.run();


    }
}