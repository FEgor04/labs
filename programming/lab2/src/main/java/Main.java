import pokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Main {
    public static void main(String[] args) {
        Battle b = new Battle();
        Pokemon p1 = new Jirachi("Cool Jirachi", 5);
        Pokemon p2 = new Snubbull("Predator", 5);
        Pokemon p3 = new Granbull("Predator", 5);
        Pokemon p4 = new Sewaddle("Predator", 5);
        Pokemon p5 = new Swadloon("Predator", 5);
        Pokemon p6 = new Leavanny("Predator", 5);

        b.addAlly(p1);
        b.addAlly(p2);
        b.addAlly(p3);
        b.addFoe(p4);
        b.addFoe(p5);
        b.addFoe(p6);
        b.go();
    }
}
