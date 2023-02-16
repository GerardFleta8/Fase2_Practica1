package Business.Monsters;

import Business.Characters.Character;

import java.util.ArrayList;

/**
 * Monster class
 */
public class Monster {

    private String name;
    private String challenge;
    private int experience;
    private int hitPoints;
    private int initiative;
    private String damageDice;
    private String damageType;
    private int numMonsters;
    /**
     * Monster constructor
     * @param other parameters for the constructor
     */
    public Monster(Monster other){
        this.name = other.getName();
        this.challenge = other.getChallenge();
        this.experience = other.getExperience();
        this.hitPoints = other.getHitPoints();
        this.initiative = other.getInitiative();
        this.damageDice = other.getDamageDice();
        this.damageType = other.getDamageType();
        this.numMonsters = 0;
    }
    /**
     * Sets number of monsters in manager
     * @param x number of monsters to set
     */
    public void setNumMonsters(int x){
        this.numMonsters = x;
    }
    /**
     * Method that gets the number of monsters
     * @return number of monsters
     */
    public int getNumMonsters(){
        return numMonsters;
    }
    /**
     * Method that gets the name of the monster
     * @return name of the monster
     */
    public String getName() {
        return name;
    }
    /**
     * Method that gets the challenge of the monster
     * @return challenge of the monster
     */
    public String getChallenge() {
        return challenge;
    }
    /**
     * Method that gets the xp of the monster
     * @return xp of the monster
     */
    public int getExperience() {
        return experience;
    }
    /**
     * Method that gets the hit points of the monster
     * @return hitpoints of the monster
     */
    public int getHitPoints() {
        return hitPoints;
    }
    /**
     * Method that gets the initiative of the monster
     * @return initiative of the monster
     */
    public int getInitiative() {
        return initiative;
    }
    /**
     * Method that gets the damage to the dice
     * @return damage to the dice
     */
    public String getDamageDice() {
        return damageDice;
    }
    /**
     * Method that gets the damage type of the monster
     * @return damage tyoe of the monster
     */
    public String getDamageType() {
        return damageType;
    }
    /**
     * Method that calculates and sets the monster initiative
     * @param d12  value of dice rolled
     */
    public void calcAndSetInitiative(int d12){
        //para un adventurer: d12 + spirit;

        int aux = d12 + this.initiative;
        this.initiative = aux;

    }
    /**
     * Method that affects the hit points of a monster
     * @param damage damage taken
     */
    public int takeDamage(int damage, String dmgType){
        hitPoints = hitPoints - damage;
        if(this.hitPoints < 0){
            this.hitPoints = 0;
        }
        return damage; //devuelve damage para saber cuanto daño a hecho el ataque realmente por las diferentes pasivas para boss
    }

    /**
     * Method which gets a monster's name and class
     * @return String with the wanted format with name and class type.
     */
    public String monsterNameAndClass(){
        String string = this.name+"("+this.challenge+")";
        return string;
    }

    /**
     * Method which checks if a monster is alive.
     * @return Boolean which indicated whether its alive or not.
     */
    public boolean isAlive(){
        if(this.hitPoints > 0){
            return true;
        }
        return false;
    }

    /**
     * Method used by monsters to realize an attack move.
     * @param party Arraylist with all the party members.
     * @return String with the result of the action.
     */
    public String attackMove(ArrayList<Character> party){
        String string;
        Character auxChar = party.get(0);
        boolean targetFound = false;
        int monsterAttackTarget = (int) (Math.random()*party.size() + 1);
        int i = 1;
        for(Character c: party){
            //correct target and target is alive:
            if((i == monsterAttackTarget) && (c.isAlive())){
                auxChar = c;

            } else if((i == monsterAttackTarget) && (!c.isAlive())) { //correct target but target is dead: need to find the next living target
                int z = i;
                while(!targetFound){
                    z = i;
                    if(party.get(z-1).isAlive()){
                        auxChar = party.get(z-1);
                        targetFound = true;
                        break;
                    }
                    i++;
                    if(i > party.size()){
                        i = 1;
                    }
                }
                if(targetFound = true){
                    break;
                }
            }
            i++;
        }
        string = "\n";
        string = string + this.getName()+ " attacks "+auxChar.getName()+".\n";
        int diceMonsterInt = Integer.parseInt(String.valueOf(this.getDamageDice().charAt(1))); //devuelve el int del damageDice del monstruo
        int d10M = (int) (Math.random()*10 + 1);
        int damageM = (int) (Math.random()*diceMonsterInt + 1);
        //int damageM = 50; //Test unconscious members
        if(d10M == 1){
            string = string + "Fails and deals 0 damage";
            damageM = 0;
        }else if(d10M == 10){
            damageM = damageM * 2;
            string = string + "Critical hit and deals "+damageM+" physical damage.";
        }else{
            string = string + "Hits and deals "+damageM+" physical damage.";
        }

        auxChar.takeDamage(damageM, this.getDamageType());
        if(!auxChar.isAlive()){
            string = string +"\n"+auxChar.getName()+" falls unconscious.";
        }
        return string;
    }

    /**
     * Method which checks if a monster is boss in order to not add more than 1 boss in the encounter.
     * @return true if boss, false if not boss
     */
    public boolean isBoss(){
        if(getChallenge().equalsIgnoreCase("Boss")){
            return true;
        }
        return false;
    }
}
