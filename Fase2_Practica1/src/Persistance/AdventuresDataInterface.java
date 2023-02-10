package Persistance;

import Business.Adventure;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public interface AdventuresDataInterface {
    void updateAdventuresFile(ArrayList<Adventure> adventures);

    ArrayList<Adventure> readAdventuresFile() throws FileNotFoundException;
}
