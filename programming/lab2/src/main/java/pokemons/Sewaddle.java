package pokemons;

import moves.status.Rest;
import moves.status.StringShot;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Sewaddle extends Pokemon {
    public Sewaddle() {
        this("Sewwadle", 1);
    }
    public Sewaddle(String name, int level) {
        super(name, level);
        this.setLevel(level);
        this.setType(Type.BUG, Type.GRASS);
        this.setStats(45, 53, 70, 40, 60, 42);
        this.addMove(new Rest());
        this.addMove(new StringShot());
    }
}
