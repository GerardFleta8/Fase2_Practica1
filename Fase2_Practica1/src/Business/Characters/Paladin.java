package Business.Characters;

import Business.Monsters.Monster;

import java.util.ArrayList;

/**
 * Paladin Class
 */
public class Paladin extends Character{
    /**
     * Paladin constructor
     * @param character Character which we want to instantiate/evolve as paladin.
     */
    public Paladin(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                "Paladin");
    }

    /**
     * Method which calculates and sets a paladin's initiative
     * @param initiative int with initiative value
     */
    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d10
        int d10;
        d10 = (int) (Math.random()*10 + 1);

        super.calcAndSetInitiative(d10 + this.getSpirit());
    }

    /**
     * Method whith the action a Paladin must perform during the warm-up stage.
     * @param party arrayList containing all party members.
     * @return String with the result of the action.
     */
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

    /**
     * Method with the action to be performed by a Paladin during the attack phase.
     * @param d10 Integer with the result of a 10 face die being rolled
     * @param party ArrayList of characters with the party members
     * @param totalMonstersEncounter ArrayList of monsters with all the current monsters in the encounter
     * @return String with the result of the action.
     */
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

    /**
     * Method which displays a Paladin's current hp.
     * @return String with the current hp.
     */
    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points";
        return string;
    }

    /**
     * Method with the action to be performed by a Paladin during the rest stage.
     * @param d8 Integer which is the result of an 8 faced die being rolled. Used to calculate heal value
     * @param party ArrayList of characters with all the party members
     * @return String with the result of the action.
     */
    @Override
    public String restStageAction(int d8, ArrayList<Character> party) {
        String string;
        if(!this.isAlive()){
            string = this.getName() + " is unconscious.";
        }else{
            int d10;
            d10 = (int) (Math.random()*10 + 1);
            int heal = d10 + this.getMind();

            ArrayList<Character> liveParty = new ArrayList<>();
            for(Character c: party){
                if(c.isAlive()){
                    liveParty.add(c);
                }
            }
            for(Character c: liveParty){
                c.takeDamage(-heal);
                if(c.getHp() > c.getMaxHP()){
                    c.setHp(c.getMaxHP());
                }
            }
            string = this.getName() + " uses Prayer of healing. Everyone heals " + heal + " hit points.";
        }
        return string;
    }

}
