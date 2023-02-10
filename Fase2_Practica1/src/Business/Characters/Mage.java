package Business.Characters;

import Business.Monsters.Monster;

import java.util.ArrayList;

/**
 * Mage class
 */
public class Mage extends Character{
    int shield;

    /**
     * constructor method for mage class
     * @param character Character which we want to initialize as a mage
     */
    public Mage(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                "Mage");
    }

    /**
     * Method that calculates the initiative for a mage
     * @param initiative int with initiative value
     */
    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d20
        int d20;
        d20 = (int) (Math.random()*20 + 1);

        super.calcAndSetInitiative(d20+this.getMind());
    }

    /**
     * Action to be performed during the warm-up stage.
     * @param party arrayList containing all party members.
     * @return String with the result of the action.
     */
    @Override
    public String warmUpAction(ArrayList<Character> party) {
        //Roll dice: d6
        int d6;
        d6 = (int) (Math.random()* 6 + 1);

        this.shield = (d6 + this.getMind()) * this.getLevel();
        String string = this.getName()+" uses Mage shield. Shield recharges to "+ this.shield +".";
        return string;
    }

    /**
     * Method which calculates the damage that the spell 'fireball' should do
     * @param d4 int corresponding to a 4 sided die
     * @return int with the damage to be dealt
     */
    public int fireball(int d4){
        return (d4 + this.getMind());
    }

    /**
     * Method which calculates the damage that the spell 'arcane missile' should do
     * @param d6 int corresponding to a 6 faced die
     * @return int with the damage to be dealt
     */
    public int arcaneMissile(int d6){
        return (d6 + this.getMind());
    }

    /**
     * Action to be performed during the combat stage by a mage.
     * @param d10 Integer with the result of a 10 face die being rolled
     * @param party ArrayList of characters with the party members
     * @param totalMonstersEncounter ArrayList of monsters with all the current monsters in the encounter
     * @return String with the result of the action that was performed.
     */
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
        string = "\n" + this.getName()+" attacks ";
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

    /**
     * Action to be performed during the rest stage, in this case, the mage does not perform any action.
     * @param d8 Integer which is the result of an 8 faced die being rolled.
     * @param party ArrayList of characters with all the party members
     * @return String with the result of the action.
     */
    @Override
    public String restStageAction(int d8, ArrayList<Character> party) {
        return null;
    }

    /**
     * Method which calculates the amount of damage that needs to be taken.
     * @param dmg int for dmg to be taken
     * @param dmgType String which indicated the damage type to be able to account for reduced damage due to passived.
     */
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

    /**
     * Method which displays the characters current hp and in this case the mage's shield value.
     * @return
     */
    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points (Shield): "+this.shield;
        return string;
    }

}
