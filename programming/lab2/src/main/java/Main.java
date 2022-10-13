import pokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Jirachi("Павел Валерьевич", 46); // Для атаки Refresh необходим 25 уровень
        Pokemon p2 = new Snubbull("Сергей Викторович", 46);
        Pokemon p3 = new Granbull("Алексей Евгеньевич", 46);
        Pokemon p6 = new Leavanny("Зураб Леванович", 46);
        Pokemon p5 = new Swadloon("Дмитрий Валерьевич", 46);
        Pokemon p4 = new Sewaddle("Алексей Владимирович", 46);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
