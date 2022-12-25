package Business.Characters;

import Business.Characters.Adventurer;
import Business.Characters.Character;
import Business.Monster;

import java.util.ArrayList;

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
    //Nose si hace falta ya que los adventurers se instanciaran como Characters c = new Adventurer()
    /*public Warrior(Adventurer adventurer){

    }*/

    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d12
        int d12;
        d12 = (int) (Math.random()*12 + 1);


        super.calcAndSetInitiative(d12 + this.getSpirit());
    }

    @Override
    public void warmUpAction(ArrayList<Character> party) {
        int currentSpirit = this.getSpirit();
        this.setSpirit(currentSpirit+1);
    }

    @Override
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> monsters) {
        int d10_2;
        d10_2 = (int) (Math.random()*12 + 1);

        int damage = 0;
        if(d10 == 1){
            damage = 0;
        }else{
            damage = d10_2 + this.getBody();
            if (d10 == 10) {
                damage = damage*2;
            }
        }
        return null;
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
