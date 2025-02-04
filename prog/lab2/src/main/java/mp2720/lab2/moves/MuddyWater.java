package mp2720.lab2.moves;

import mp2720.lab2.Dice;
import ru.ifmo.se.pokemon.*;

public class MuddyWater extends SpecialMove {
    public MuddyWater() {
        super(Type.WATER, 90, 85);
    }

    @Override
    protected void applyOppEffects(Pokemon p) {
        if (Dice.roll(30))
            p.setMod(Stat.ATTACK, -1);
    }
}
