package Business.Monsters;

import Business.Characters.Character;

import static java.lang.Math.floor;

public class Boss extends Monster{
    public Boss(Monster monster){
        super(monster);
    }

    @Override
    public int takeDamage(int damage, String dmgType) {
        int dmgToTake = 0;
        if(this.getDamageType().equalsIgnoreCase(dmgType)){
            dmgToTake = (int) floor(damage/2);
        }
        int dmgTaken = super.takeDamage(dmgToTake, dmgType);
        return dmgTaken;
    }
}
