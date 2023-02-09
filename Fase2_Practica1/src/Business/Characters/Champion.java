package Business.Characters;

import Business.Monsters.Monster;

import java.util.ArrayList;

public class Champion extends Character{
    public Champion(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                "Champion");
    }

    @Override
    public String attackAction(int d10, ArrayList<Character> party, ArrayList<Monster> totalMonstersEncounter) {
        int d10_2;
        d10_2 = (int) (Math.random()*12 + 1);

        int damage = 0;
        if(d10 == 1){
            damage = 0;
        }else{
            damage = d10_2 + this.getBody();
            if (d10 == 10) {
                damage = damage*2;
            }
        }


        ArrayList<Monster> liveMonsters = new ArrayList<>();
        for(Monster m : totalMonstersEncounter){
            if(m.isAlive()){
                liveMonsters.add(m);
            }
        }
        Monster aux = liveMonsters.get(0);
        for(Monster c : liveMonsters){
            if(aux.getHitPoints() > c.getHitPoints()) {
                aux = c;
            }
        }
        String s3 = null;
        String s = "\n"+this.getName()+" attacks "+aux.getName()+" with Improved sword slash.";
        String s2;

        if(d10 == 1){
            s2 = s + "\nFails and deals 0 damage";
        }else if(d10 == 10){
            s2 = s + "\nCritical hit and deals "+damage+" physical damage.";
        }else{
            s2 = s + "\nHits and deals "+damage+" physical damage.";
        }


        aux.takeDamage(damage, this.getClassType());
        if(aux.getHitPoints() <= 0){
            s3 = s2 + "\n"+ aux.getName()+" dies.";
        }
        else{
            s3 = s2;
        }
        return s3;
    }

    @Override
    public void calcAndSetInitiative(int initiative) {
        //Roll dice: d12
        int d12;
        d12 = (int) (Math.random()*12 + 1);


        super.calcAndSetInitiative(d12 + this.getSpirit());
    }

    @Override
    public String warmUpAction(ArrayList<Character> party) {
        for(Character c: party){
            int currentSpirit = c.getSpirit();
            c.setSpirit(currentSpirit + 1);
        }
        String string = this.getName()+" uses Motivational speech. Everyone's Spirit increases in +1.";
        return string;
    }

    @Override
    public void calcAndSetMaxHP() {
        int hp = (10 + this.getBody())*this.getLevel() + this.getBody()*this.getLevel();
        this.setHp(hp);
    }

    @Override
    public int getMaxHP() {
        int hp = (10 + this.getBody())*this.getLevel() + this.getBody()*this.getLevel();
        return hp;
    }

    @Override
    public String displayCurrentHp() {
        String string = "\t- "+this.getName()+"\t"+this.getHp()+" / "+this.getMaxHP()+" hit points";
        return string;
    }

    @Override
    public String restStageAction(int d8, ArrayList<Character> party) {
        String string;
        if(!this.isAlive()){
            string = this.getName() + " is unconscious.";
        }else{
            int heal = this.getMaxHP()-this.getHp();
            this.calcAndSetMaxHP();
            string = this.getName() + " uses Improved bandage time. Heals "+heal+" hit points.";
        }
        return string;
    }

}
