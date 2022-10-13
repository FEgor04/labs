package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ThunderWaveTest extends MoveTest {
    @Test
    @DisplayName("ThunderWave")
    @Tag("status_move")
    public void testAttack() {
        StatusMove thunderWaveAttack = new ThunderWave();
        assertEquals(Status.NORMAL, defender.getCondition());
        thunderWaveAttack.attack(attacker, defender);
        assertEquals(Status.PARALYZE, defender.getCondition());
    }
}
