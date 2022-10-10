package pokemons;

import moves.status.SwordsDance;

public class Leavanny extends Swadloon {
    public Leavanny(String name, int level) {
        super(name, level);
        this.setStats(75, 103, 80, 70, 80, 92);
        this.addMove(new SwordsDance());
    }

    public Leavanny() {
        super("Leavanny", 1);
    }
}
