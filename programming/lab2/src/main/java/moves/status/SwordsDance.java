package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Swords Dance raises the user's Attack by two stages.
 */
public class SwordsDance extends StatusMove {
    public SwordsDance() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected String describe() {
        return "использует Swords Dance";
    }
    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().stat(Stat.ATTACK, 2).turns(-1));
    }


}
