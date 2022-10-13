package moves.status;

import jdk.jfr.Description;
import moves.MoveTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.Effect;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RefreshTest extends MoveTest {
    @Test
    @DisplayName("Test Refresh BURN")
    @Description("Should remove BURN effect")
    @Tag("status_move")
    public void testAttack() {
        Effect.burn(attacker);
        StatusMove refreshAttack = new Refresh();
        refreshAttack.attack(attacker, defender);
        assertEquals(attacker.getCondition(), Status.NORMAL);
    }

    @Test
    @DisplayName("Freeze")
    @Description("Should not remove FREEZE effect")
    @Tag("status_move")
    public void testAttackFreeze() {
        Effect.freeze(attacker);
        assertEquals(Status.FREEZE, attacker.getCondition(), "default state should be FREEZE");

        StatusMove refreshAttack = new Refresh();
        refreshAttack.attack(attacker, defender);
//        super.testAttack();
        assertEquals(Status.FREEZE, attacker.getCondition(), "state should not change");
    }
}
