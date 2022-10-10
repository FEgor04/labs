package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * String Shot lowers the target's Speed by two stages.
 */
public class StringShot extends StatusMove {
    public StringShot() {
        super(Type.BUG, 0, 95);
    }

    @Override
    protected String describe() {
        return "использует String Shot";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.setCondition(new Effect().stat(Stat.SPEED, -2));
    }
}
