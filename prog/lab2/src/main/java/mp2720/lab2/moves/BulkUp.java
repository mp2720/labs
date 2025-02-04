package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class BulkUp extends StatusMove {
    public BulkUp() {
        super(Type.FIGHTING, 0, 100);
    }

    @Override
    public void applySelfEffects(Pokemon p) {
        p.setMod(Stat.ATTACK, 1);
        p.setMod(Stat.DEFENSE, 1);
    }
}
