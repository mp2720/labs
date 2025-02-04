package mp2720.lab2.moves;

import mp2720.lab2.Dice;
import ru.ifmo.se.pokemon.*;

public class Scald extends SpecialMove {
    public Scald() {
        super(Type.FIRE, 80, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Dice.roll(30))
            Effect.burn(p);
    }
}
