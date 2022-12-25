package Business;
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
    public void takeDamage(int damage, String dmgType){
        hitPoints = hitPoints - damage;
    }

    public String monsterNameAndClass(){
        String string = this.name+"("+this.challenge+")";
        return string;
    }
}
