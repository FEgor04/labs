package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Grass Whistle puts the target to sleep, if it hits. Sleeping Pokémon cannot move (with some exceptions such as Snore). Sleep lasts for 1-3 turns.
 */
public class GrassWhistle extends StatusMove {
    public GrassWhistle() {
        super(Type.GRASS, 100, 55);
    }

    @Override
    protected String describe() {
        return "использует Grass Whistle";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().turns(2).condition(Status.SLEEP));
    }
}
