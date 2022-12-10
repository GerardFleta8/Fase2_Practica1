package Business;

import com.google.gson.annotations.SerializedName;

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
    private int actionPerformed;
    private int level;

    public Character(String name, String player, int xp, int body, int mind, int spirit, String classType) {
        this.name = name;
        this.player = player;
        this.xp = xp;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.classType = classType;
        initiative = 0;
    }

    public String getName(){
        return name;
    }
    public String getPlayer() {return player; }
    public Integer getBody() {return body; }
    public int getMind() {return mind; }
    public int getXp() {return xp;}
    public int getSpirit() {return spirit; }

    public int getHp(){return hp;}

    public int getLevel(){return level;}
    public String getClassType() {return classType; }

    public void calcAndSetInitiative(int d12){
        //para un adventurer: d12 + spirit;
        this.initiative = d12 + spirit;
    }

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

    public void warmUpAction(){
        //como ahora solo tenemos adventurers, solo incrementa el spirit
        this.spirit++;
    }
    public int getInitiative() {
        return this.initiative;
    }
    public int getActionPerformed(){
        return this.actionPerformed;
    }
    public void setActionPerformed(int actionPerformed){
        this.actionPerformed = actionPerformed;
    }

    public int calcAndSetLevel(int experience){
        this.xp = this.xp + experience;
        if(xp > 0 && xp <= 99){
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
    public void calcAndSetMaxHP(){
        this.hp = (10 + body)*level;
    }
    public int getMaxHP(){
        int maxHp = (10 + body)*level;
        return maxHp;
    }
    public void takeDamage(int dmg){
        hp = hp - dmg;
    }
}
