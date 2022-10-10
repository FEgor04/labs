package pokemons;

import moves.special.DazzlingGleam;
import moves.status.CalmMind;
import moves.status.Refresh;
import moves.status.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Jirachi extends ru.ifmo.se.pokemon.Pokemon {
    public Jirachi() {
        this("Jirachi", 1);
    }
    public Jirachi(String name, int level) {
        super(name, level);
        this.setLevel(level);
        this.setType(Type.STEEL, Type.PSYCHIC);
        this.setStats(100, 100, 100, 100, 100, 100);
        this.addMove(new DazzlingGleam());
        this.addMove(new Refresh());
        this.addMove(new CalmMind());
        this.addMove(new ThunderWave());
    }

}
