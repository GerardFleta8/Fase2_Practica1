package Business;

public class Character {
    private String name;
    private String owner;
    private int xp;
    private int body;
    private int mind;
    private int spirit;
    private String classType;

    public Character(String name, String owner, int xp, int body, int mind, int spirit) {
        this.name = name;
        this.owner = owner;
        this.xp = xp;
        this.body = body;
        this.mind = mind;
        this.spirit = spirit;

    }

    public String getName(){
        return name;
    }
    public String getOwner() {return owner; }
    public Integer getBody() {return body; }
    public int getMind() {return mind; }
    public int getSpirit() {return spirit; }
    public String getClassType() {return classType; }

}
