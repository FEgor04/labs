package moves.special;

import moves.MoveTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.ifmo.se.pokemon.SpecialMove;
import ru.ifmo.se.pokemon.Stat;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ShadowBallTest extends MoveTest {
    @Test
    @DisplayName("ShadowBall")
    @Tag("special_move")
    @Tag("broken")
    @Disabled
    public void testAttack() {
        SpecialMove shadowBallAttack = new ShadowBall(100);
        double oldHp = defender.getHP();
        double oldSpecialDefense = defender.getStat(Stat.SPECIAL_DEFENSE);

        shadowBallAttack.attack(attacker, defender);
        double currentDefense = defender.getStat(Stat.SPECIAL_DEFENSE);
        double currentHp = defender.getHP();

        assertTrue(oldHp > currentHp, "old hp should be greater than current");
        assertTrue(oldSpecialDefense >= currentDefense,
                String.format("old special defense (%.2f) should be greater or equal than current (%.2f)", oldSpecialDefense, currentDefense)
        );
    }
}
