package pokemons;

import moves.physical.RockSlide;

public class Granbull extends Snubbull {
    public Granbull(String name, int level) {
        super(name, level);
        this.setStats(90, 120, 75, 60, 60, 45);
        this.addMove(new RockSlide());
    }

    public Granbull() {
        this("Granbull", 1);
    }
}
