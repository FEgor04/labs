package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SwordsDanceTest extends MoveTest {
    @Test
    @DisplayName("SwordsDance")
    @Tag("status_move")
    public void testAttack() {
        StatusMove swordsDanceAttack = new SwordsDance();
        double oldAttack = attacker.getStat(Stat.ATTACK);
        swordsDanceAttack.attack(attacker, defender);
        assertTrue(oldAttack < attacker.getStat(Stat.ATTACK), "old attack should be less than current");
    }
}
