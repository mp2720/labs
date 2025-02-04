package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class SludgeBomb extends SpecialMove {
    public SludgeBomb() {
        super(Type.POISON, 90, 100);
    }

    @Override
    protected void applyOppEffects(Pokemon target) {
        Effect effect = new Effect().chance(0.3).condition(Status.POISON);
        target.addEffect(effect);
    }
}
