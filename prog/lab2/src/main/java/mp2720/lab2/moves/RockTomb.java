package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class RockTomb extends PhysicalMove {
    public RockTomb() {
        super(Type.ROCK, 60, 95);
    }

    @Override
    protected void applyOppEffects(Pokemon target) {
        target.setMod(Stat.SPEED, -1);
    }
}
