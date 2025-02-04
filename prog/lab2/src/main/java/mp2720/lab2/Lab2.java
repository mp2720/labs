package mp2720.lab2;

import mp2720.lab2.pokemons.*;
import ru.ifmo.se.pokemon.Battle;
import ru.ifmo.se.pokemon.Pokemon;

public class Lab2 {
    public static void main(String[] args) {
        Battle b = new Battle();

        Pokemon kyogre = new Kyogre("Kyogre", 1), budew = new Budew("Budew", 1),
                mankey = new Mankey("Mankey", 1), primeape = new Primeape("Primeape", 1),
                roselia = new Roselia("Roselia", 1), roserade = new Roserade("Roserade", 1);

        b.addAlly(kyogre);
        b.addAlly(roselia);
        b.addAlly(roserade);
        b.addFoe(mankey);
        b.addFoe(primeape);
        b.addFoe(budew);
        b.go();
    }
}
