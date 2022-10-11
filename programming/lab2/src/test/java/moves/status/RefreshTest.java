package moves.status;

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
    @DisplayName("Test Refresh Attack Effects")
    @Tag("status_move")
    public void testAttack() {
        Effect fireCondition = new Effect().condition(Status.BURN);
        attacker.setCondition(fireCondition);

        StatusMove refreshAttack = new Refresh();
        refreshAttack.attack(attacker, defender);
        assertEquals(attacker.getCondition(), Status.NORMAL);
    }
}
