package Business.Characters;

import Business.Monster;

import java.util.ArrayList;

public class Paladin extends Character{
    public Paladin(Character character){
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
        //Roll dice: d10
        int d10;
        d10 = (int) (Math.random()*10 + 1);

        super.calcAndSetInitiative(d10 + this.getSpirit());
    }

    @Override
    public String warmUpAction(ArrayList<Character> party) {
        //Roll dice: d3
        int d3;
        d3 = (int) (Math.random()* 3 + 1);

        for(Character c: party){
            int currentMind = c.getMind();
            c.setMind(currentMind + d3);
        }
        String string = this.getName()+" uses Blessing of good luck. Everyone's Mind increases in +"+d3+".";
        return string;
    }

    @Override
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter) {
        int heal = d10 + this.getMind();

        int d8;
        d8 = (int) (Math.random()*8 + 1);
        int dmg = d8 + this.getSpirit();

        String actionTaken = null;
        boolean healingAction = false;

        ArrayList<Character> liveParty = new ArrayList<>();
        for(Character c: party){
            if(c.isAlive()){
                liveParty.add(c);
            }
        }

        for(Character c : liveParty){
            if(c.hpLessThanHalf()){
                healingAction = true;
            }
        }
        if(healingAction){
            for(Character c: liveParty){
                c.takeDamage(-heal);
                if(c.getHp() > c.getMaxHP()){
                    c.setHp(c.getMaxHP());
                }
            }
            actionTaken = this.getName() + " uses Prayer of healing. Heals " + heal + " hit points to ";
            int i = 1;
            for(Character c: liveParty){
                if (i == liveParty.size()) {
                    actionTaken = actionTaken + " and "+ c.getName()+".";
                }
                else if(i == liveParty.size() - 1){
                    actionTaken = actionTaken + c.getName();
                }
                else {
                    actionTaken = actionTaken + c.getName() + ", ";
                }
                i++;
            }

        }else{
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
            String s = this.getName() + " attacks " + aux.getName() +" with Never on my watch.\n";
            if(d10_2 == 1){
                actionTaken =  s + "Fails and deals 0 damage\n";
            }else if(d10_2 == 10){
                actionTaken = s + "Critical hit and deals "+endDmg+" physical damage.\n";
            }else{
                actionTaken = s + "Hits and deals "+endDmg+" physical damage.\n";
            }
            if(!aux.isAlive()){
                actionTaken = actionTaken + aux.getName() + " dies.\n";
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
    public String restStageAction(int d8) {
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
