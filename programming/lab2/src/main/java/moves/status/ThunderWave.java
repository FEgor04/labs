package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Paralyzes the target.
 */
public class ThunderWave extends StatusMove {

    public ThunderWave() {
        super(Type.ELECTRIC, 100, 90);
    }

    @Override
    protected String describe() {
        return "использует Thunder Wave";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect.paralyze(pokemon);
    }
}
