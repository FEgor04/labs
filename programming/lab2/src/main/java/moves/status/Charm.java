package moves.status;

import ru.ifmo.se.pokemon.*;

/**
 * Charm lowers the target's Attack by two stages.
 * <a href="https://pokemondb.net/move/charm">Pokemon DB description</a>
 */
public class Charm extends StatusMove {
    public Charm() {
        super(Type.FAIRY, 0, 100);
    }

    @Override
    protected boolean checkAccuracy(Pokemon pokemon, Pokemon pokemon1) {
        return true;
    }

    @Override
    protected String describe() {
        return "использует Charm";
    }

    @Override
    protected void applyOppEffects(Pokemon pokemon) {
        pokemon.addEffect(new Effect().stat(Stat.ATTACK, -2).turns(-1));
    }
}
