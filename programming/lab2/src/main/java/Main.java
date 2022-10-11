import pokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Jirachi("Балакшин", 25); // Для атаки Refresh необходим 25 уровень
        Pokemon p2 = new Snubbull("Клименков", 8);
        Pokemon p3 = new Granbull("Письмак", 8);
        Pokemon p6 = new Leavanny("Зураб Леванович", 46);
        Pokemon p5 = new Swadloon("Карпов", 1);
        Pokemon p4 = new Sewaddle("Пастор", 1);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
