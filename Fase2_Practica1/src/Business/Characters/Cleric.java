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
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d10
        int d10;
        d10 = (int) (Math.random()*10 + 1);

        super.calcAndSetInitiative(d10 + this.getSpirit());
    }

    @Override
    public void warmUpAction(ArrayList<Character> party) {
        for(Character c: party){
            int currentMind = c.getMind();
            c.setMind(currentMind + 1);
        }
    }
}
