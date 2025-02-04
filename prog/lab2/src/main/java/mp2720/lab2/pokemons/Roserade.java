package mp2720.lab2.pokemons;

import mp2720.lab2.moves.*;

public class Roserade extends Budew {
    public Roserade(String name, int level) {
        super(name, level);

        setStats(60, 70, 65, 125, 105, 90);
        addMove(new SwordsDance());
    }
}
