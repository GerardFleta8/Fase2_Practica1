package Persistance;

import Business.Monsters.Monster;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public interface MonsterDataInterface {
    ArrayList<Monster> readMonstersFile() throws FileNotFoundException, IOException;

    boolean isEmpty() throws IOException;
}
