package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.*;

public class GrassWhistleTest extends MoveTest {
    @Test
    @DisplayName("Test Grass Whistle Attack Effects")
    @Tag("status_move")
    public void testAttack() {
        StatusMove grassWhistleAttack = new GrassWhistle();
        grassWhistleAttack.attack(attacker, defender);
        assertEquals(defender.getCondition(), Status.SLEEP);
    }

}
