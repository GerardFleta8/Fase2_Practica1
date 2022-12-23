package Business.Characters;

import Business.Monster;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Character {
    private String name;
    private String player;
    private int xp;
    private int body;
    private int mind;
    private int spirit;
    @SerializedName("class")
    private String classType;
    private int initiative;
    private int hp;
    private int level;

    /**
     * Constructor characters
     * @param name String with the character's name
     * @param player String with the character's player's name
     * @param xp Integer with the character's experience points
     * @param body Integer with the value for the parameter: body
     * @param mind Integer with the value for the parameter: mind
     * @param spirit Integer with the value for the parameter: spirit
     * @param classType String which indicates the character's class type
     */
    public Character(String name, String player, int xp, int body, int mind, int spirit, String classType) {
        this.name = name;
        this.player = player;
        this.xp = xp;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.classType = classType;
        initiative = 0;
        level = this.calcAndSetLevel(0);
    }

    /**
     * Gets a character's name
     * @return String with characters name
     */
    public String getName(){
        return name;
    }

    /**
     * Gets a character's player
     * @return String with a character's player
     */
    public String getPlayer() {return player; }

    /**
     * Gets a character's body value
     * @return Integer with character's body value
     */
    public Integer getBody() {return body; }

    /**
     * Gets a character's mind value
     * @return Integer with character's mind value
     */
    public int getMind() {return mind; }

    /**
     * Gets a character's experience points
     * @return Integer with a character's experience points
     */
    public int getXp() {return xp;}

    /**
     * Gets a character's spirit value
     * @return Integer with character's spirit value
     */
    public int getSpirit() {return spirit; }

    /**
     * Gets a character's current hp
     * @return Integer with character's current hp
     */
    public int getHp(){return hp;}

    /**
     * Gets character's level
     * @return Integer with character's level
     */
    public int getLevel(){return level;}

    /**
     * Gets a character's class type
     * @return String with character's class type
     */
    public String getClassType() {return classType; }

    /**
     * Calculates and sets a character's initiative
     * @param initiative
     */
    public void calcAndSetInitiative(int initiative){
        //para un adventurer: d12 + spirit;
        this.initiative = initiative;
    }

    /**
     * Is the action to be performed during the rest stage (only if a character is alive)
     * @param d8 Integer which is the result of an 8 faced die being rolled. Used to calculate heal value
     * @return Int with the heal value, to be able to print it.
     */
    public int restStageAction(int d8){
        if(hp <= 0){
            return 0;
        }else{
            int heal = d8 + mind;
            hp = hp + heal;
            return heal;
        }
    }

    /**
     * Action to be performed during the attack stage of a battle
     * @param d10 Integer with the result of a 10 face die being rolled
     * @param d6 Integer with the result of a 6 face die being rolled
     * @return Integer with the amount of damage done by the attack
     */
    public int attackAction(int d10, int d6){

        int damage = 0;
        if(d10 == 1){
            damage = 0;
        }else{
            damage = d6 + body;
            if (d10 == 10) {
                damage = damage*2;
            }
        }
        return damage;
    }

    /**
     * Action to be performed during the warm-up stage of a battle
     */
    /*public void warmUpAction(){
        //como ahora solo tenemos adventurers, solo incrementa el spirit
        this.spirit++;
    }*/
    public void warmUpAction(ArrayList<Character> party){

    }

    /**
     * Gets a character's initiative
     * @return Integer with a character's initiative
     */
    public int getInitiative() {
        return this.initiative;
    }

    /**
     * Calculates and sets a characters level based on their experience
     * @param experience is the experience the character just gained
     * @return Integer with the character new level after being recalculated
     */
    public int calcAndSetLevel(int experience){
        this.xp = this.xp + experience;
        if(xp >= 0 && xp <= 99){
            level = 1;
        }
        if(xp > 99 && xp <= 199){
            level = 2;
        }
        if(xp > 199 && xp <= 299){
            level = 3;
        }
        if(xp > 299 && xp <= 399){
            level = 4;
        }
        if(xp > 399 && xp <= 499){
            level = 5;
        }
        if(xp > 499 && xp <= 599){
            level = 6;
        }
        if(xp > 599 && xp <= 699){
            level = 7;
        }
        if(xp > 699 && xp <= 799){
            level = 8;
        }
        if(xp > 799 && xp <= 899){
            level = 9;
        }
        if(xp > 899){
            level = 10;
        }
        return level;
    }

    /**
     * Calculates the max hp based on current level and body stats and sets the current hp to max hp
     * (it is used when a character levels up and when initialy setting the hp)
     */
    public void calcAndSetMaxHP(){
        this.hp = (10 + body)*level;
    }

    /**
     * Calculates the max hp based on current level and body stats without setting the current hp to it
     * (its used when printing current hp / max hp during battle phase)
     * @return
     */
    public int getMaxHP(){
        int maxHp = (10 + body)*level;
        return maxHp;
    }


    //will be used in subClasses to reduce dmg taken
    public void takeDamage(int dmg, String dmgType){
        hp = hp - dmg;
    }
    /**
     * The damage received is subtracted from a character's current hp
     * @param dmg Integer with the damage a character should receive
     */
    public void takeDamage(int dmg){
        hp = hp - dmg;
        if(hp < 0){
            hp = 0;
        }
    }

    /*
    //For subclasses to be able to set name
    public void setName(String name) {
        this.name = name;
    }
    */
    public void setHp(int hp){
        this.hp = hp;
    }
    public void setSpirit(int spirit){
        this.spirit = spirit;
    }

    protected void setMind(int mind) {
        this.mind = mind;
    }

    public String characterDetails(){
        String string = "Tavern keeper: \"Hey " + name + " get here; the boss wants to see you!\"\n\n"+"* Name:   "+name+"\n* Player: "+player+"\n* Class:  "+classType+"\n* Level:  "+level+"\n* XP:     "+xp+"\n";
        String stringBody;
        String stringMind;
        String stringSpirit;
        if(body >= 0){
            stringBody = string + "* Body:   +"+body+"\n";
        }else{
            stringBody = string + "* Body:   -"+body+"\n";
        }
        if(mind >= 0){
            stringMind = stringBody + "* Mind:   +"+mind+"\n";
        }else{
            stringMind = stringBody + "* Mind:   -"+mind+"\n";
        }
        if(spirit >= 0){
            stringSpirit = stringMind + "* Spirit: +"+spirit+"\n";
        }else{
            stringSpirit = stringMind + "* Spirit: -"+spirit+"\n";
        }
        return stringSpirit;
    }
}
