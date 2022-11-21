import Business.Character;
import Persistance.CharactersJsonDAO;
import Presentation.Controller;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.run();
        //commit prova
    }
}