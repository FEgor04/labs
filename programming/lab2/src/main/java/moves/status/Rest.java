package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * User sleeps for 2 turns, but user is fully healed.
 */
public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect restEffect = new Effect().turns(2).condition(Status.SLEEP);
        pokemon.restore();
        pokemon.setCondition(restEffect);
    }

    @Override
    protected String describe() {
        return "пошел отдохнуть)";
    }
}
