package moves.status;

import moves.MoveTest;
import org.junit.jupiter.api.*;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Stat;
import ru.ifmo.se.pokemon.StatusMove;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CharmTest extends MoveTest {
    @Test
    @DisplayName("Charm")
    @Tag("status_move")
    @Tag("broken")
    @Disabled
    public void testAttack() {
        double oldAttack = defender.getStat(Stat.ATTACK);
        logger.info("oldAttack: " + oldAttack);
        StatusMove charmAttack = new Charm();
        charmAttack.attack(attacker, defender);

        assertTrue(oldAttack > defender.getStat(Stat.ATTACK),
                String.format("old attack (%.2f) should be greater than current (%.2f)",
                        oldAttack,
                        defender.getStat(Stat.ATTACK)));
    }
}