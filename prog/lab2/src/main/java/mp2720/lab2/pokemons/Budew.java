package mp2720.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import mp2720.lab2.moves.*;

public class Budew extends Pokemon {
    public Budew(String name, int level) {
        super(name, level);

        setType(Type.GRASS);
        setStats(40, 30, 35, 50, 70, 55);

        setMove(new Rest(), new SludgeBomb());
    }
}
