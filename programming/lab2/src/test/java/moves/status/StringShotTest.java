package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringShotTest extends MoveTest {
    @Test
    @Tag("status_move")
    @Tag("broken")
    @Disabled
    public void testAttack() {
        double oldSpeed = defender.getStat(Stat.SPEED);
        StatusMove stringShotAttack = new StringShot(100);
        stringShotAttack.attack(attacker,defender);
        assertTrue(
                oldSpeed > defender.getStat(Stat.SPEED),
                String.format("old speed (%.2f) should be greater than current (%.2f)", oldSpeed, defender.getStat(Stat.SPEED)));
    }
}
