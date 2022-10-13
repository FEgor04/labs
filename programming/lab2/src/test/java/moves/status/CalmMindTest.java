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
        double oldSpecialAttack = attacker.getStat(Stat.SPECIAL_ATTACK);
        double oldSpecialDefense = attacker.getStat(Stat.SPECIAL_DEFENSE);
        StatusMove calmMindAttack = new CalmMind();
        calmMindAttack.attack(attacker, defender);
        assertTrue(oldSpecialAttack < attacker.getStat(Stat.SPECIAL_ATTACK),
                "old special attack (" + oldSpecialAttack + ") should be less or equal current" + "(" + attacker.getStat(Stat.SPECIAL_ATTACK) + ")");
        assertTrue(oldSpecialDefense < attacker.getStat(Stat.SPECIAL_DEFENSE),
                "old special defense should be less than current");
    }
}
