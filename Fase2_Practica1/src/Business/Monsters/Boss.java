package Business.Monsters;

import Business.Characters.Character;

import java.util.ArrayList;

import static java.lang.Math.floor;

/**
 * Class for monsters that are of tpe boss.
 */
public class Boss extends Monster{

    /**
     * Constructor for Boss monsters
     * @param monster
     */
    public Boss(Monster monster){
        super(monster);
    }

    /**
     * Method which allows a boss to take damage taking into account the incoming damage type.
     * @param damage int with the damage to take.
     * @param dmgType string with the damage type.
     * @return int with the damage finally taken.
     */
    @Override
    public int takeDamage(int damage, String dmgType) {
        int dmgToTake = 0;
        if(this.getDamageType().equalsIgnoreCase(dmgType)){
            dmgToTake = (int) floor(damage/2);
        }
        int dmgTaken = super.takeDamage(dmgToTake, dmgType);
        return dmgTaken;
    }

    /**
     * Method which allows a boss to attack all party members.
     * @param party Arraylist containing all memebrs of the party.
     * @return String with the result of the action.
     */
    @Override
    public String attackMove(ArrayList<Character> party) {
        String string;
        ArrayList<Character> liveParty = new ArrayList<>();
        for(Character c : party){
            if(c.isAlive()){
                liveParty.add(c);
            }
        }

        string = "\n";
        string = string + this.getName()+ " attacks ";
        int i = 1;
        for(Character c : liveParty){
            if (i == liveParty.size()) {
                string = string + " and "+ c.getName()+".";
            }
            else if(i == liveParty.size() - 1){
                string = string + c.getName();
            }
            else {
                string = string + c.getName() + ", ";
            }
            i++;
        }
        int diceMonsterInt = Integer.parseInt(String.valueOf(this.getDamageDice().charAt(1))); //devuelve el int del damageDice del monstruo
        int d10M = (int) (Math.random()*10 + 1);
        int damageM = (int) (Math.random()*diceMonsterInt + 1);
        //int damageM = 50; //Test unconscious members
        if(d10M == 1){
            string = string + "\nFails and deals 0 damage";
            damageM = 0;
        }else if(d10M == 10){
            string = string + "\nCritical hit and deals "+damageM+" physical damage.";
            damageM = damageM * 2;
        }else{
            string = string + "\nHits and deals "+damageM+" physical damage.";
        }
        for(Character c: liveParty){
            c.takeDamage(damageM, this.getDamageType());
            if(!c.isAlive()){
                string = string + "\n"+ c.getName() + " falls unconscious.";
            }
        }
        return string;
    }
}
