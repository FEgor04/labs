package moves;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.ifmo.se.pokemon.Pokemon;

public class MoveTest {
    protected static Pokemon attacker = new Pokemon("attacker", 10);
    protected static Pokemon defender = new Pokemon("defender", 10);

    @BeforeAll
    public static void setUpTests() {
        attacker.setStats(100, 100, 100, 100, 100, 100);
        defender.setStats(100, 100, 100, 100, 100, 100);
    }

    @AfterEach
    public void tearDownTest() {
        attacker.restore();
        defender.restore();
    }
}
