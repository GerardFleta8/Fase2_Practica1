package Business.Characters;

import Business.Monsters.Monster;

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
                "Mage");
    }

    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d20
        int d20;
        d20 = (int) (Math.random()*20 + 1);

        super.calcAndSetInitiative(d20+this.getMind());
    }

    @Override
    public String warmUpAction(ArrayList<Character> party) {
        //Roll dice: d6
        int d6;
        d6 = (int) (Math.random()* 6 + 1);

        this.shield = (d6 + this.getMind()) * this.getLevel();
        String string = this.getName()+" uses Mage shield. Shield recharges to "+ this.shield +".";
        return string;
    }

    public int fireball(int d4){
        return (d4 + this.getMind());
    }

    public int arcaneMissile(int d6){
        return (d6 + this.getMind());
    }

    @Override
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter) {
        int mCounter = 0;
        int damage = 0;
        String string = null;
        for(Monster m : totalMonstersEncounter){
            if(m.isAlive()){
                mCounter++;
            }
        }
        string = this.getName()+" attacks ";
        if(mCounter >= 3){
            int d4;
            d4 = (int) (Math.random()*4 + 1);
            damage = fireball(d4);
            if(d10 == 10){
                damage = damage * 2;
            }else if(d10 == 1){
                damage = 0;
            }
            ArrayList<Monster> liveMonsters = new ArrayList<>();
            for(Monster m : totalMonstersEncounter){
                if(m.isAlive()){
                    m.takeDamage(damage, "Magic");
                    liveMonsters.add(m);
                }
            }
            int i = 1;
            for (Monster m : liveMonsters){
                if(i == liveMonsters.size()){
                    string = string + " and " + m.getName()+ " with Fireball.";
                } else if(i == liveMonsters.size()-1){
                    string = string + m.getName();
                }
                else{
                    string = string + m.getName() + ", ";
                }
                i++;
            }
            if(d10 == 1){
                string =  string + "\nFails and deals 0 damage.\n";
            }else if(d10 == 10){
                string = string + "\nCritical hit and deals "+damage+" physical damage.\n";
            }else{
                string = string + "\nHits and deals "+damage+" physical damage.\n";
            }
            for (Monster m : liveMonsters){
                if(!m.isAlive()){
                    string = string + m.getName()+" dies.\n";
                }
            }

        }else{
            int d6;
            d6 = (int) (Math.random()*6 + 1);
            damage = arcaneMissile(d6);
            if(d10 == 10){
                damage = damage * 2;
            }else if(d10 == 1){
                damage = 0;
            }
            Monster aux = totalMonstersEncounter.get(0);
            //atacar al que mas vida tiene
            for(Monster m : totalMonstersEncounter){
                if(m.getHitPoints() > aux.getHitPoints()){
                    aux = m;
                }
            }
            aux.takeDamage(damage,"Magic");
            string = string + aux.getName()+ " with Arcane missile.";
            if(d10 == 1){
                string =  string + "\nFails and deals 0 damage\n";
            }else if(d10 == 10){
                string = string + "\nCritical hit and deals "+damage+" physical damage.\n";
            }else{
                string = string + "\nHits and deals "+damage+" physical damage.\n";
            }
            if(!aux.isAlive()){
                string = string + aux.getName()+" dies.";
            }
        }
        return string;
    }

    @Override
    public String restStageAction(int d8, ArrayList<Character> party) {
        return null;
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

    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points (Shield): "+this.shield;
        return string;
    }
}
