package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.Status;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.*;

public class RestTest extends MoveTest {
    @Test
    @DisplayName("Test Rest Attack")
    @Tag("status_move")
    public void testAttack() {
        double startHp = attacker.getHP();
        StatusMove restAttack = new Rest();
        attacker.setMod(Stat.HP, 50);
        assertEquals(startHp - 50, attacker.getHP(), "attacker hp should be -50");
        restAttack.attack(attacker, defender);
        assertEquals(startHp, attacker.getHP());
        assertEquals(Status.SLEEP, attacker.getCondition());
    }
}
