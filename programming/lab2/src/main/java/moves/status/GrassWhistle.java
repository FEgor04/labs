package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Grass Whistle puts the target to sleep, if it hits. Sleeping Pokémon cannot move (with some exceptions such as Snore). Sleep lasts for 1-3 turns.
 */
public class GrassWhistle extends StatusMove {
    public GrassWhistle() {
        this(55);
    }

    /**
     * Конструктор с указанием точности, необходим для тестов
     * @param acc точность
     */
    public GrassWhistle(double acc) {
        super(Type.GRASS, 100, acc);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return (accuracy == 1) || super.checkAccuracy(pokemon, pokemon1);
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
