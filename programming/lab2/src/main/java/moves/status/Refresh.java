package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Cleanses the user of a burn, paralysis, or poison.
 */
public class Refresh extends StatusMove {
    public Refresh() {
        super(Type.NORMAL, 0, 100);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "использует Refresh";
    }

    @Override
    protected void applySelfEffects(Pokemon p) {
        if (p.getCondition() == Status.BURN || p.getCondition() == Status.PARALYZE || p.getCondition() == Status.POISON) {
            Effect normalCondition = new Effect();
            normalCondition.condition(Status.NORMAL);
            p.setCondition(normalCondition);
        }
    }
}
