package mp2720.lab2.pokemons;

import mp2720.lab2.moves.*;

public class Primeape extends Mankey {
    public Primeape(String name, int level) {
        super(name, level);

        setStats(65, 105, 60, 60, 70, 96);
        addMove(new StoneEdge());
    }
}
