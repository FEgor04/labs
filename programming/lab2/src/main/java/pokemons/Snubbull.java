package pokemons;

import moves.special.ShadowBall;
import moves.status.Charm;
import moves.status.ThunderWave;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;

public class Snubbull extends Pokemon {
    public Snubbull(String name, int level) {
        super(name, level);
        this.setLevel(level);
        this.setType(Type.FAIRY);
        this.setStats(60, 80, 50, 40, 40, 30);
        this.addMove(new ThunderWave());
        this.addMove(new ShadowBall());
        this.addMove(new Charm());
    }

    public Snubbull() {
        this("Snubbull", 1);
    }
}
