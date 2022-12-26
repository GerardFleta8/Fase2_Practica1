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
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter) {
        //Roll dice: d6
        int d6;
        d6 = (int) (Math.random()*6 + 1);

        int damage = 0;
        if(d10 == 1){
            damage = 0;
        }else{
            damage = d6 + this.getBody();
            if (d10 == 10) {
                damage = damage*2;
            }
        }


        Monster aux = totalMonstersEncounter.get(0);
        for(Monster c : totalMonstersEncounter){
            if(aux.getHitPoints() > c.getHitPoints() && c.getHitPoints() >= 0) {
                aux = c;
            }else if(aux.getHitPoints() <= 0 && c.getHitPoints() >= 0){
                aux = c;
            }
        }
        String s3 = null;
            String s = "\n"+this.getName()+" attacks "+aux.getName()+" with Sword slash.";
            String s2;

            if(d10 == 1){
                s2 = s + "\nFails and deals 0 damage";
            }else if(d10 == 10){
                s2 = s + "\nCritical hit and deals "+damage+" physical damage.";
            }else{
                s2 = s + "\nHits and deals "+damage+" physical damage.";
            }


            aux.takeDamage(damage, this.getClassType());
            if(aux.getHitPoints() <= 0){
                s3 = s2 + "\n"+ aux.getName()+" dies.";
            }
            else{
                s3 = s2;
            }
        return s3;
    }
}
