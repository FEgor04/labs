package pokemons;

import moves.status.GrassWhistle;

public class Swadloon extends Sewaddle {
    public Swadloon(String name, int level) {
        super(name, level);
        this.setStats(55, 63, 90, 50, 80, 42);
        this.addMove(new GrassWhistle());
    }

    public Swadloon() {
        this("Swadloon", 1);
    }
}
