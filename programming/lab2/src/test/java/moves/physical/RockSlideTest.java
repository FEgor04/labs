package moves.physical;

import moves.MoveTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.PhysicalMove;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RockSlideTest extends MoveTest {
    @Test
    @DisplayName("RockSlide")
    @Tag("physical_move")
    public void testAttack() {
        PhysicalMove rockSlideAttack = new RockSlide(100);
        double oldHp = defender.getHP();
        rockSlideAttack.attack(attacker, defender);

        assertTrue(oldHp > defender.getHP(), "old hp should be greater than current");
    }
}
