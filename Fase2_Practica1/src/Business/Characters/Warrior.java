package Business.Characters;

import Business.Monsters.Monster;

import java.util.ArrayList;

/**
 * Warrior class
 */
public class Warrior extends Character {
    /**
     * Constructor method for Warrior
     * @param character Character which we want to initialize as a warrior.
     */
    public Warrior(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                "Warrior");
    }

    /**
     * Method which calculates and sets a warrior's initiative
     * @param initiative int with initiative value
     */
    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d12
        int d12;
        d12 = (int) (Math.random()*12 + 1);


        super.calcAndSetInitiative(d12 + this.getSpirit());
    }

    /**
     * Method with the action to be performed by a warrior during the warm-up stage
     * @param party arrayList containing all party members.
     * @return String with the result of the action
     */
    @Override
    public String warmUpAction(ArrayList<Character> party) {
        int currentSpirit = this.getSpirit();
        this.setSpirit(currentSpirit+1);
        String string = this.getName()+" uses Self-motivated. Their Spirit increases in +1.";
        return string;
    }

    /**
     * Action to be performed by a warrior during the attack phase of a combat
     * @param d10 Integer with the result of a 10 face die being rolled
     * @param party ArrayList of characters with the party members
     * @param totalMonstersEncounter ArrayList of monsters with all the current monsters in the encounter
     * @return
     */
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
        String s = "\n"+this.getName()+" attacks "+aux.getName()+" with Improved sword slash.";
        String s2;

        if(d10 == 1){
            s2 = s + "\nFails and deals 0 damage";
        }else if(d10 == 10){
            s2 = s + "\nCritical hit and deals "+damage+" physical damage.";
        }else{
            s2 = s + "\nHits and deals "+damage+" physical damage.";
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

    /**
     * Method that calculates the damage that should be taken by a warrior depending on the damage type.
     * @param dmg int for dmg to be taken
     * @param dmgType String which indicated the damage type to be able to account for reduced damage due to passived.
     */
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

    /**
     * Method which displays a warrior's current hp.
     * @return
     */
    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points";
        return string;
    }

    /**
     * Method with the action to be performed by a warrior during the rest stage.
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
            int heal = d8 + this.getMind();
            int hp = this.getHp();

            if(hp + heal > this.getMaxHP()){
                this.setHp(this.getMaxHP());
                heal = this.getMaxHP() - hp;
            }else {
                this.setHp(hp + heal);
            }

            string = this.getName() + " uses Bandage time. Heals "+heal+" hit points.";
        }
        return string;
    }

    /**
     * Method which adds xp to a warrior and checks if he has leveled up or has to evolve.
     * @param xp int with the xp to add
     * @return String with the result of the xp added.
     */
    @Override
    public String addXp(int xp){
        int currentLevel = this.getLevel();
        int newLevel = this.calcAndSetLevel(xp);
        String string = null;
        if(newLevel > currentLevel){
            //level up;
            string = this.getName()+" gains "+xp+" xp. "+this.getName()+" levels up. They are now lvl "+newLevel+"!\n";
            this.calcAndSetMaxHP(); //100% hp when lvl up
            //check evolve.
            if(newLevel >= 8 && currentLevel < 8){
                string = string + this.getName() + " evolves to Champion!\n";
            }

        }
        else{
            string = this.getName()+" gains "+xp+" xp.\n";
        }
        return string;
    }
}
