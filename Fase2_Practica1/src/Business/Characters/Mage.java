package Business.Characters;

import Business.Monster;

import java.util.ArrayList;

public class Mage extends Character{
    int shield;
    public Mage(Character character){
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
        //Roll dice: d6
        int d6;
        d6 = (int) (Math.random()* 6 + 1);

        this.shield = (d6 + this.getMind()) * this.getLevel();
    }

    public int fireball(int d4){
        return (d4 + this.getMind());
    }

    public int arcaneMissile(int d6){
        return (d6 + this.getMind());
    }

    @Override
    public int restStageAction(int d8) {
        return 0;
    }

    @Override
    public void takeDamage(int dmg, String dmgType) {
        if(dmgType.equalsIgnoreCase(this.getClassType())){
            int dmgToTake;
            dmgToTake = dmg - this.getLevel();
            if(dmgToTake < 0){
                dmgToTake = 0;
            }
            if(this.shield > 0 ){
                this.shield = this.shield - dmgToTake;
                if(shield < 0){
                    int remainingDmg;
                    remainingDmg = -shield;
                    super.takeDamage(remainingDmg);
                    shield = 0;
                }
            } else{
                super.takeDamage(dmgToTake);
            }
        }else{
            if(this.shield > 0 ){
                this.shield = this.shield - dmg;
                if(shield < 0){
                    int remainingDmg;
                    remainingDmg = -shield;
                    super.takeDamage(remainingDmg);
                    shield = 0;
                }
            } else{
                super.takeDamage(dmg);
            }
        }
    }
}
