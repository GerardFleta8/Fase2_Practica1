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

    public Character(String name, String player, int xp, int body, int mind, int spirit, String classType) {
        this.name = name;
        this.player = player;
        this.xp = xp;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;
        this.classType = classType;

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


}
