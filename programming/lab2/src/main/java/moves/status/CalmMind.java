package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Raises the user's Special Attack and Special Defense by one stage.
 */
public class CalmMind extends StatusMove {
    public CalmMind() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "использует Calm Mind";
    }

    @Override
    protected void applySelfEffects(Pokemon pokemon) {
        Effect calmMindEffect = new Effect().
                stat(Stat.SPECIAL_ATTACK, +1).
                stat(Stat.SPECIAL_DEFENSE, +1).
                turns(-1);
        pokemon.addEffect(calmMindEffect);
    }
}
