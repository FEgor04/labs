package moves.physical;

import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.PhysicalMove;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

/**
 * Rock Slide deals damage and has a 30% chance of causing the target to flinch (if the target has not yet moved).
 */
public class RockSlide extends PhysicalMove {
    /**
     * Вероятность того, что атака наложит эффект flinch
     * При необходимости, может быть установлена на значение, большее 1
     * В таком случае, атака будет срабатывать ВСЕГДА, и всегда будет накладываться эффект flinch
     * Это необходимо для тестирования атаки.
     */
    private double flinchAccuracy = 0.3;

    /**
     * Дефолтный конструктор для атаки
     */
    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return (flinchAccuracy >= 1) || super.checkAccuracy(pokemon, pokemon1);
    }

    /**
     * Конструктор с возможностью переопределния вероятности flicnh
     * @param acc вероятность flinch
     */
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
