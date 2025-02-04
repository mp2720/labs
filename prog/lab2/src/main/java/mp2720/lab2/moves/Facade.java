package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class Facade extends PhysicalMove {
    public Facade() {
        super(Type.NORMAL, 70, 100);
    }

    @Override
    protected double calcBaseDamage(Pokemon attacker, Pokemon target) {
        double damage = super.calcBaseDamage(attacker, target);
        Status status = attacker.getCondition();
        if (status == Status.BURN || status == Status.POISON || status == Status.PARALYZE)
            damage *= 2;
        return damage;
    }
}
