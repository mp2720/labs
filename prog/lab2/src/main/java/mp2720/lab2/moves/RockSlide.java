package mp2720.lab2.moves;

import mp2720.lab2.Dice;
import ru.ifmo.se.pokemon.*;

public class RockSlide extends PhysicalMove {
    public RockSlide() {
        super(Type.ROCK, 75, 90);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Dice.roll(30))
            Effect.flinch(p);
    }
}
