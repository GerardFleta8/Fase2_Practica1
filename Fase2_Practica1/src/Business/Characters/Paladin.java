package Business.Characters;

import java.util.ArrayList;

public class Paladin extends Character{
    public Paladin(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                character.getClassType());
    }

    @Override
    public void warmUpAction(ArrayList<Character> party) {
        //Roll dice: d3
        int d3;
        d3 = (int) (Math.random()* 3 + 1);

        for(Character c: party){
            int currentMind = c.getMind();
            c.setMind(currentMind + d3);
        }
    }
}
