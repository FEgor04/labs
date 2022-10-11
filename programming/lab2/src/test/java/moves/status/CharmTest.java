package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharmTest extends MoveTest {
    @Test
    @DisplayName("Test Attack Effects")
    @Tag("status_move")
    public void testAttack() {
        StatusMove charmAttack = new Charm();
        charmAttack.attack(attacker, defender);
        assertTrue(100 > defender.getStat(Stat.ATTACK),
                "old attack should be greater than current, but it is" + defender.getStat(Stat.ATTACK));
    }
}