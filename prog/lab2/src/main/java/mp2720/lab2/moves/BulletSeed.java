package mp2720.lab2.moves;

import mp2720.lab2.Dice;
import ru.ifmo.se.pokemon.*;

public class BulletSeed extends SpecialMove {
    public BulletSeed() {
        super(Type.GRASS, 25, 100);
    }

    @Override
    protected double calcBaseDamage(Pokemon attacker, Pokemon target) {
        double strikeDamage = super.calcBaseDamage(attacker, target);

        int strikesCount = 0;

        if (Dice.roll(3, 8))
            ++strikesCount;
        if (Dice.roll(3, 8))
            ++strikesCount;

        if (Dice.roll(1, 8))
            ++strikesCount;
        if (Dice.roll(1, 8))
            ++strikesCount;

        return strikeDamage * strikesCount;
    }
}
