package moves.special;

import ru.ifmo.se.pokemon.*;

/**
 * Shadow Ball deals damage and has a 20% chance of lowering the target's Special Defense by one stage.
 *
 * Stats can be lowered to a minimum of -6 stages each.
 */
public class ShadowBall extends SpecialMove {
    public ShadowBall() {
        super(Type.GHOST, 80, 100);
    }

    @Override
    protected String describe() {
        return "использует Shadow Ball";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        Effect shadowBallEffect = new Effect().chance(0.2).stat(Stat.SPECIAL_DEFENSE, -1);
        pokemon.setCondition(shadowBallEffect);
    }
}
