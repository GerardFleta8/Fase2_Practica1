package Business.Characters;

public class Mage extends Character{
    public Mage(Character character){
        super(character.getName(),
                character.getPlayer(),
                character.getXp(),
                character.getBody(),
                character.getMind(),
                character.getSpirit(),
                character.getClassType());
    }
    int shield;
}
