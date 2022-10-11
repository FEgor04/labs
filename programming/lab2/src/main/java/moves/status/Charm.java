package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Charm lowers the target's Attack by two stages.
 */
public class Charm extends StatusMove {
    public Charm() {
        super(Type.FAIRY, 0, 100);
    }

    @Override
    protected String describe() {
        return "использует Charm";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect charmMoveEffect = new Effect().stat(Stat.ATTACK, -2);
        pokemon.setCondition(charmMoveEffect);
    }
}
