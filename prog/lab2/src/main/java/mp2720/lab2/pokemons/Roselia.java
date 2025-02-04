package mp2720.lab2.pokemons;

import mp2720.lab2.moves.*;

public class Roselia extends Budew {
    public Roselia(String name, int level) {
        super(name, level);

        setStats(50, 60, 45, 100, 80, 65);
        addMove(new BulletSeed());
    }
}
