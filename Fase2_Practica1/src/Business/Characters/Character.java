package Business.Characters;

import Business.Monsters.Monster;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Character class
 */
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
     * @param initiative int with initiative value
     */
    public void calcAndSetInitiative(int initiative){
        //para un adventurer: d12 + spirit;
        this.initiative = initiative;
    }

    /**
     * Is the action to be performed during the rest stage (only if a character is alive)
     * @param d8 Integer which is the result of an 8 faced die being rolled. Used to calculate heal value
     * @param party ArrayList of characters with all the party members
     * @return Int with the heal value, to be able to print it.
     */
    public String restStageAction(int d8, ArrayList<Character> party){
        return null;
    }

    /**
     * Action to be performed during the attack stage of a battle
     * @param d10 Integer with the result of a 10 face die being rolled
     * @param party ArrayList of characters with the party members
     * @param totalMonstersEncounter ArrayList of monsters with all the current monsters in the encounter
     * @return Integer with the amount of damage done by the attack
     */
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter){

        /*int damage = 0;
        if(d10 == 1){
            damage = 0;
        }else{
            damage = d6 + body;
            if (d10 == 10) {
                damage = damage*2;
            }
        }
        return damage;*/
        return null;
    }

    /**
     * Action to be performed during the warm-up stage of a battle
     * @param party arrayList containing all party members.
     * @return String Result of the action in the wanted format
     */
    public String warmUpAction(ArrayList<Character> party){

        return null;
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


    /**
     * Method which will be implemented by subclasses in order to take into account the incoming damage type to reduce the damage taken
     * @param dmg int for dmg to be taken
     * @param dmgType String which indicated the damage type to be able to account for reduced damage due to passived.
     */
    public void takeDamage(int dmg, String dmgType){
        hp = hp - dmg;
        if(hp < 0){
            setHp(0);
        }
    }
    /**
     * The damage received is subtracted from a character's current hp
     * @param dmg Integer with the damage a character should receive
     */
    public void takeDamage(int dmg){
        hp = hp - dmg;
        if(hp < 0){
            setHp(0);
        }
    }


    /**
     * Setter for character Hp
     * @param hp int for new hp value
     */
    public void setHp(int hp){
        this.hp = hp;
    }

    /**
     * Sets the character's Spirit parameter (is used for example by champion warm up stage action)
     * @param spirit int which is the new spirit parameter
     */
    public void setSpirit(int spirit){
        this.spirit = spirit;
    }

    /**
     * Sets the character's Mind parameter (is used for example by paladin warm up stage action)
     * @param mind int which is the new Mind parameter
     */
    protected void setMind(int mind) {
        this.mind = mind;
    }

    /**
     * Method which places the wanted information regarding the character's details into the needed format
     * @return String which has the message with info needed.
     */
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

    /**
     * Method that checks if a character is alive
     * @return Boolean whih indicated true when character is alive, and false, when charater is dead.
     */
    public boolean isAlive(){
        if(this.hp > 0){
            return true;
        }
        return false;
    }

    /**
     * Method that checks if a character's hp is less than half (this is used for example by the paladin healing action)
     * @return Boolean indicated whether the character has less than/equal half of its hp(true) or not(false)
     */
    public boolean hpLessThanHalf(){
        if(this.hp < this.getMaxHP()/2){
            return true;
        }
        return false;
    }

    /**
     * Displays the information needed before each round regarding character HP.
     * @return String with the corresponding output
     */
    public String displayCurrentHp(){
        return null;
    }

    /**
     * Method that adds the necessary xp to the character and checks if the character has leveled up
     * @param xp int with the xp to add
     * @return Returns a string which shows the message with its corresponding format including the necessary info.
     */
    public String addXp(int xp){
        int currentLevel = this.level;
        int newLevel = this.calcAndSetLevel(xp);
        String string = null;
        if(newLevel > currentLevel){
            //level up;
            string = this.name+" gains "+xp+" xp. "+this.name+" levels up. They are now lvl "+newLevel+"!\n";
            this.calcAndSetMaxHP(); //100% hp when lvl up
        }
        else{
            string = this.getName()+" gains "+xp+" xp.\n";
        }
        return string;
    }
}
