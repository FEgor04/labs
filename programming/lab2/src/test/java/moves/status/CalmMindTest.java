package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.*;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CalmMindTest extends MoveTest {
    @Test
    @DisplayName("Test Attack Effects")
    @Tag("status_move")
    public void testAttack() {
        StatusMove calmMindAttack = new CalmMind();
        calmMindAttack.attack(attacker, defender);
        assertTrue(100 > attacker.getStat(Stat.SPECIAL_ATTACK),
                "old special attack should be less than current");
        assertTrue(100 > attacker.getStat(Stat.SPECIAL_DEFENSE),
                "old special defense should be less than current");
    }
}
