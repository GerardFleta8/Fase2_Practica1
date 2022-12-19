package Business.Characters;

import java.util.ArrayList;

public class Cleric extends Character{
    public Cleric(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                character.getClassType());
    }

    @Override
    public void warmUpAction(int d6, int d3, ArrayList<Character> party) {
        for(Character c: party){
            int currentMind = c.getMind();
            c.setMind(currentMind + 1);
        }
    }
}
