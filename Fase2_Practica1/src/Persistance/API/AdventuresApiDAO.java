package Persistance.API;

import Business.Adventure;
import Business.Characters.Character;
import Persistance.AdventuresDataInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class in charge with interacting with t API
 */
public class AdventuresApiDAO implements AdventuresDataInterface {
    ApiHelper apiHelper;

    {
        try {
            apiHelper = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method which updates the adventures to the API
     * @param adventures List containing the adventures we want to update.
     */
    @Override
    public void updateAdventuresFile(ArrayList<Adventure> adventures) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            apiHelper.deleteFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_15/adventures");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            for (Adventure c: adventures) {
                apiHelper.postToUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_15/adventures", gson.toJson(c));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Method which reads the adventures found on the API
     * @return ArrayList</Adventure> list with the adventures that have been read.
     * @throws FileNotFoundException
     */
    @Override
    public ArrayList<Adventure> readAdventuresFile() throws FileNotFoundException {
        Gson g = new Gson();
        String response;

        try {
            response = apiHelper.getFromUrl("https://balandrau.salle.url.edu/dpoo/S1-Project_15/adventures");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Adventure adventure[] = g.fromJson(response, Adventure[].class);

        ArrayList<Adventure> adventures = new ArrayList<>();
        if (adventure == null) {
            return new ArrayList<>();
        }
        for(Adventure a: adventure){
            adventures.add(a);
        }
        return adventures;
    }
}
