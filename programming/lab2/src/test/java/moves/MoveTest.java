package moves;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import ru.ifmo.se.pokemon.Move;
import ru.ifmo.se.pokemon.Pokemon;

import java.util.logging.Logger;

public class MoveTest {
    protected static Pokemon attacker = new Pokemon("attacker", 20);
    protected static Pokemon defender = new Pokemon("defender", 20);

    protected static final Logger logger = Logger.getLogger(MoveTest.class.getName());

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
