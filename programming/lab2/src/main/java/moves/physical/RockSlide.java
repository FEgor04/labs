package moves.physical;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

/**
 * Rock Slide deals damage and has a 30% chance of causing the target to flinch (if the target has not yet moved).
 */
public class RockSlide extends PhysicalMove {
    private double flinchAccuracy = 0.3;

    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return (flinchAccuracy >= 1) || super.checkAccuracy(pokemon, pokemon1);
    }

    public RockSlide(double acc) {
        this();
        flinchAccuracy = acc;
    }

    @Override
    protected String describe() {
        return "использует Rock Slide";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        if (Math.random() < flinchAccuracy) {
            Effect.flinch(pokemon);
        }
    }
}
