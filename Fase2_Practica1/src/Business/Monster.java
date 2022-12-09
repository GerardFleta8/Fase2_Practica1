package Business;

public class Monster {

    private String name;
    private String challenge;
    private int experience;
    private int hitPoints;
    private int initiative;
    private String damageDice;
    private String damageType;
    private int numMonsters;
    private int actionPerformed;

    public Monster(String name, String challenge, String xp, String hitPoints, String initiative, String damageDice, String damageType) {
    }

    public void setNumMonsters(int x){
        this.numMonsters = x;
    }
    public int getNumMonsters(){
        return numMonsters;
    }
    public String getName() {
        return name;
    }

    public String getChallenge() {
        return challenge;
    }

    public int getExperience() {
        return experience;
    }

    public int getHitPoints() {
        return hitPoints;
    }

    public int getInitiative() {
        return initiative;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public String getDamageType() {
        return damageType;
    }

    public int getActionPerformed(){
        return this.actionPerformed;
    }
    public void setActionPerformed(int actionPerformed){
        this.actionPerformed = actionPerformed;
    }

    public void calcAndSetInitiative(int d12){
        //para un adventurer: d12 + spirit;
        System.out.println("initial initiave func:" + this.initiative);
        int aux = d12 + this.initiative;
        this.initiative = aux;
        System.out.println("final initiave func:" + this.initiative);
    }
}
