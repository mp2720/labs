package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class SwordsDance extends StatusMove {
    public SwordsDance() {
        super(Type.GRASS, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon attacker) {
        attacker.setMod(Stat.ATTACK, 2);
    }
}
