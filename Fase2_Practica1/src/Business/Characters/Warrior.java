package Business.Characters;

import Business.Characters.Adventurer;
import Business.Characters.Character;

public class Warrior extends Character {
    public Warrior(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                character.getClassType());
    }

    //crear un warrior desde una clase character si hace extends de Adventurer y no character
    /*public Warrior(Character character){
        super(character);
    }*/

    //crear un warrior desde una clase Adventurer (cuando evolucionan)
    /*public Warrior(Adventurer adventurer){

    }*/

    @Override
    public int attackAction(int d10, int d10_2) {
        int damage = 0;
        if(d10 == 1){
            damage = 0;
        }else{
            damage = d10_2 + this.getBody();
            if (d10 == 10) {
                damage = damage*2;
            }
        }
        return damage;
    }

    @Override
    public void takeDamage(int dmg, String dmgType) {
        int dmgToTake = 0;
        if(dmgType.equalsIgnoreCase(this.getClassType())){
            dmgToTake = dmg/2;
            super.takeDamage(dmgToTake);
        }else{
            super.takeDamage(dmg);
        }
    }
}
