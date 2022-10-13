package moves.special;

import moves.MoveTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.SpecialMove;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DazzlingGleamTest extends MoveTest {
    @Test
    @DisplayName("DazzlingGleam")
    @Tag("special_move")
    @Tag("broken")
    public void testAttack() {
        SpecialMove dazzlingGleamAttack = new DazzlingGleam();
        double oldHp = defender.getHP();
        dazzlingGleamAttack.attack(attacker, defender);
        assertTrue(oldHp > defender.getHP(), "old hp should be greater than current");
    }
}
