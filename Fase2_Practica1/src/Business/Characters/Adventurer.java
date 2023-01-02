package Business.Characters;

import Business.Monsters.Monster;

import java.lang.reflect.Array;
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
    public String warmUpAction(ArrayList<Character> party) {
        int currentSpirit = this.getSpirit();
        this.setSpirit(currentSpirit+1);
        String string = this.getName()+" uses Self-motivated. Their Spirit increases in +1.";
        return string;
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
        ArrayList<Monster> liveMonsters = new ArrayList<>();
        for(Monster m : totalMonstersEncounter){
            if(m.isAlive()){
                liveMonsters.add(m);
            }
        }
        Monster aux = liveMonsters.get(0);
        for(Monster c : liveMonsters){
            if(aux.getHitPoints() > c.getHitPoints()) {
                aux = c;
            }
        }
        String s3 = null;
            String s = "\n"+this.getName()+" attacks "+aux.getName()+" with Sword slash.";
            String s2;

            if(d10 == 1){
                s2 = s + "\nFails and deals 0 damage";
            }else if(d10 == 10){
                s2 = s + "\nCritical hit and deals "+damage+" physical damage.\n";
            }else{
                s2 = s + "\nHits and deals "+damage+" physical damage.\n";
            }


            aux.takeDamage(damage, this.getClassType());
            if(aux.getHitPoints() <= 0){
                s3 = s2 + aux.getName()+" dies.\n";
            }
            else{
                s3 = s2;
            }
        return s3;
    }

    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points";
        return string;
    }

    @Override
    public String restStageAction(int d8, ArrayList<Character> party) {
        String string;
        if(!this.isAlive()){
            string = this.getName() + " is unconscious.";
        }else{
            int heal = d8 + this.getMind();
            int hp = this.getHp();

            if(hp + heal > this.getMaxHP()){
                this.calcAndSetMaxHP();
                heal = this.getMaxHP() - hp;
            }else {
                this.setHp(hp + heal);
            }

            string = this.getName() + " uses Bandage time. Heals "+heal+" hit points.";
        }
        return string;
    }
}
