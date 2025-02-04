package mp2720.lab2.pokemons;

import ru.ifmo.se.pokemon.*;
import mp2720.lab2.moves.*;

public class Kyogre extends Pokemon {
    public Kyogre(String name, int level) {
        super(name, level);

        setStats(100, 100, 90, 150, 140, 90);
        setType(Type.WATER);

        setMove(new AquaTail(), new MuddyWater(), new RockSlide(), new Scald());
    }
}
