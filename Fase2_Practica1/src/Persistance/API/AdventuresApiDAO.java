package Persistance.API;

import Business.Adventure;
import Business.Characters.Character;
import Persistance.AdventuresDataInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class AdventuresApiDAO implements AdventuresDataInterface {
    ApiHelper apiHelper;

    {
        try {
            apiHelper = new ApiHelper();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
