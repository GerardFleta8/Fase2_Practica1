package Business.Characters;

import java.util.ArrayList;

public class Champion extends Character{
    public Champion(Character character){
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
            int currentSpirit = c.getSpirit();
            c.setSpirit(currentSpirit + 1);
        }
    }
}
