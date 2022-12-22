package Business.Characters;

import Business.Monster;

import java.util.ArrayList;

public class Adventurer extends Character {
    public Adventurer(Character character){
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
        int currentSpirit = this.getSpirit();
        this.setSpirit(currentSpirit+1);
    }
}
