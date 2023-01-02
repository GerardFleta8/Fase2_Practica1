package Business.Characters;

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
    public String warmUpAction(ArrayList<Character> party) {
        int currentSpirit = this.getSpirit();
        this.setSpirit(currentSpirit+1);
        String string = this.getName()+" uses Self-motivated. Their Spirit increases in +1.";
        return string;
    }

    @Override
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter) {
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


        Monster aux = totalMonstersEncounter.get(0);
        for(Monster c : totalMonstersEncounter){
            if(aux.getHitPoints() > c.getHitPoints() && c.getHitPoints() >= 0) {
                aux = c;
            }else if(aux.getHitPoints() <= 0 && c.getHitPoints() >= 0){
                aux = c;
            }
        }
        String s3 = null;
        String s = "\n"+this.getName()+" attacks "+aux.getName()+" with Improved sword slash.";
        String s2;

        if(d10 == 1){
            s2 = s + "\nFails and deals 0 damage\n";
        }else if(d10 == 10){
            s2 = s + "\nCritical hit and deals "+damage+" physical damage.\n";
        }else{
            s2 = s + "\nHits and deals "+damage+" physical damage.\n";
        }


        aux.takeDamage(damage, this.getClassType());
        if(!aux.isAlive()){
            s3 = s2 + "\n"+ aux.getName()+" dies.";
        }
        else{
            s3 = s2;
        }
        return s3;
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

    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points";
        return string;
    }

    @Override
    public String restStageAction(int d8) {
        String string;
        if(!this.isAlive()){
            string = this.getName() + " is unconscious.";
        }else{
            int heal = d8 + this.getMind();
            int hp = this.getHp();
            this.setHp(hp + heal);

            string = this.getName() + " uses Bandage time. Heals "+heal+" hit points.";
        }
        return string;
    }
}
