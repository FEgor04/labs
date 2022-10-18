package moves.special;

import ru.ifmo.se.pokemon.*;

/**
 * Shadow Ball deals damage and has a 20% chance of lowering the target's Special Defense by one stage.
 */
public class ShadowBall extends SpecialMove {
    /**
     * Шанс на понижение SPECIAL_DEFENSE.
     * Необходим для тестов
     */
    private double specialDefenseLowerChance = 0.2;

    public ShadowBall() {
        super(Type.GHOST, 80, 100);
    }

    public ShadowBall(double acc) {
        this();
        specialDefenseLowerChance = acc;
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "использует Shadow Ball";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        System.out.println(specialDefenseLowerChance);
        pokemon.addEffect(new Effect().chance(specialDefenseLowerChance).stat(Stat.SPECIAL_DEFENSE, -1));
    }
}
