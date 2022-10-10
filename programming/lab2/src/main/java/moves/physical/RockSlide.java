package moves.physical;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

/**
 * Rock Slide deals damage and has a 30% chance of causing the target to flinch (if the target has not yet moved).
 */
public class RockSlide extends PhysicalMove {
    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }

    @Override
    protected String describe() {
        return "использует Rock Slide";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect rockSlideEffect = new Effect().chance(0.3);
        if (rockSlideEffect.success()) {
            Effect.flinch(pokemon);
        }
    }
}
