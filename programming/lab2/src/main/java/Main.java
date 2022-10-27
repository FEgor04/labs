import pokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        var b = new Battle();
        var p1 = new Jirachi("Павел Валерьевич", 46); // Для атаки Refresh необходим 25 уровень
        var p2 = new Snubbull("Сергей Викторович", 46);
        var p3 = new Granbull("Алексей Евгеньевич", 46);
        var p6 = new Leavanny("Зураб Леванович", 46);
        var p5 = new Swadloon("Дмитрий Валерьевич", 46);
        var p4 = new Sewaddle("Алексей Владимирович", 46);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
