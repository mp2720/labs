package mp2720.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import mp2720.lab2.moves.*;

public class Mankey extends Pokemon {
    public Mankey(String name, int level) {
        super(name, level);

        setStats(40, 80, 35, 35, 45, 70);
        setType(Type.WATER);

        setMove(new BulkUp(), new Facade(), new RockTomb());
    }
}
