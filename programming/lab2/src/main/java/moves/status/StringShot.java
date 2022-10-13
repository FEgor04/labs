package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * String Shot lowers the target's Speed by two stages.
 */
public class StringShot extends StatusMove {
    public StringShot() {
        this(95);
    }

    /**
     * Констурктор для тестов.
     * Необходим для 100% точности атаки
     * @param acc
     */
    public StringShot(double acc) {
        super(Type.BUG, 0, acc);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return (accuracy == 100) || super.checkAccuracy(pokemon, pokemon1);
    }

    @Override
    protected String describe() {
        return "использует String Shot";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().stat(Stat.SPEED, -2).turns(-1));
    }
}
