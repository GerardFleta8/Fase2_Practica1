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

    public Character(String name, String player, int xp, int body, int mind, int spirit, String classType) {
        this.name = name;
        this.player = player;
        this.xp = xp;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.classType = classType;
        initiative = 0;
        hp = 0;

    }

    public String getName(){
        return name;
    }
    public String getPlayer() {return player; }
    public Integer getBody() {return body; }
    public int getMind() {return mind; }
    public int getXp() {return xp;}
    public int getSpirit() {return spirit; }
    public String getClassType() {return classType; }

    public void calcAndSetInitiative(int d12){
        //para un adventurer: d12 + spirit;
        this.initiative = d12 + spirit;
    }

    public void warmUpAction(){
        //como ahora solo tenemos adventurers, solo incrementa el spirit
        this.spirit++;
    }


    public int getInitiative() {
        return this.initiative;
    }
}
