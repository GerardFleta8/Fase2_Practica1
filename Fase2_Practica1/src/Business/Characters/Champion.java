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
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d12
        int d12;
        d12 = (int) (Math.random()*12 + 1);


        super.calcAndSetInitiative(d12 + this.getSpirit());
    }

    @Override
    public void warmUpAction(ArrayList<Character> party) {
        for(Character c: party){
            int currentSpirit = c.getSpirit();
            c.setSpirit(currentSpirit + 1);
        }
    }

    @Override
    public void calcAndSetMaxHP() {
        int hp = (10 + this.getBody())*this.getLevel() + this.getBody()*this.getLevel();
        this.setHp(hp);
    }
}
