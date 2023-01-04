package Business.Characters;

import Business.Monsters.Monster;

import java.util.ArrayList;

public class Cleric extends Character{
    public Cleric(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                "Cleric");
    }

    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d10
        int d10;
        d10 = (int) (Math.random()*10 + 1);

        super.calcAndSetInitiative(d10 + this.getSpirit());
    }

    @Override
    public String warmUpAction(ArrayList<Character> party) {
        for(Character c: party){
            int currentMind = c.getMind();
            c.setMind(currentMind + 1);
        }
        String string = this.getName()+" uses Prayer of good luck. Everyone's Mind increases in +1.";
        return string;
    }

    @Override
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter) {
        int heal = d10 + this.getMind();
        boolean healAction = false;

        int d4;
        d4 = (int) (Math.random()*4 + 1);
        int dmg = d4 + this.getSpirit();

        String actionTaken = null;
        for(Character c : party){
            //busca si hay alguno de la party con menos de la mitad de hp y lo cura(a el primero y tambien a el mismo si es el)
            if(c.hpLessThanHalf() && c.isAlive()){
                //Prayer of Healing:
                healAction = true;
                c.takeDamage(-heal); //takedmg: hp = hp - dmg ---> to heal:  hp = hp - (-heal)
                if(c.getHp() > c.getMaxHP()){
                    heal = heal - (c.getHp() - c.getMaxHP());
                    c.setHp(c.getMaxHP());
                }
                actionTaken = this.getName() + " uses Prayer of healing. Heals " + heal + " hit points to " + c.getName()+".\n";
                break;
            }
        }

        //Not on my watch:
        if(!healAction){
            int target;
            target = (int) (Math.random()*totalMonstersEncounter.size() + 1);
            int i = 1;
            Monster aux = null;
            boolean targetFound = false;
            for(Monster m : totalMonstersEncounter){
                if(i == target && m.isAlive()){
                    aux = m; //target found;
                    break;
                } else if (i == target && !m.isAlive()) {
                    int z = i;
                    while(!targetFound){
                        z = i;
                        if(totalMonstersEncounter.get(z-1).isAlive()){
                            aux = totalMonstersEncounter.get(z-1);
                            targetFound = true;
                            break;
                        }
                        i++;
                        if(i > totalMonstersEncounter.size()){
                            i = 1;
                        }
                    }
                    if(targetFound){
                        break;
                    }
                }
                i++;
            }
            int d10_2;
            d10_2 = (int) (Math.random()*10 + 1);
            if(d10_2 == 1){
                dmg = 0;
            }
            if(d10_2 == 10){
                dmg = dmg * 2;
            }

            int endDmg = aux.takeDamage(dmg, "Psychical");
            String s = this.getName() + " attacks " + aux.getName() +" with Not on my watch.";
            if(d10_2 == 1){
                actionTaken =  s + "\nFails and deals 0 damage";
            }else if(d10_2 == 10){
                actionTaken = s + "\nCritical hit and deals "+endDmg+" physical damage.";
            }else{
                actionTaken = s + "\nHits and deals "+endDmg+" physical damage.";
            }
        }
        return actionTaken;
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
            int d10;
            d10 = (int) (Math.random()*10 + 1);
            int heal = d10 + this.getMind();
            int hp = this.getHp();
            if(hp + heal > this.getMaxHP()){
                this.calcAndSetMaxHP();
                heal = this.getMaxHP() - hp;
            }else {
                this.setHp(hp + heal);
            }

            string = this.getName() + " uses Prayer of self-healing. Heals "+heal+" hit points.";
        }
        return string;
    }
}
