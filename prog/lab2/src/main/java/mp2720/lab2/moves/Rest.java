package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class Rest extends StatusMove {
    public Rest() {
        super(Type.PSYCHIC, 0, 100);
    }

    @Override
    protected void applySelfEffects(Pokemon attacker) {
        Effect effect = new Effect().turns(2).condition(Status.SLEEP);
        attacker.addEffect(effect);
        double diff = attacker.getStat(Stat.HP) - attacker.getHP();
        attacker.setMod(Stat.HP, (int) diff);
        System.out.println(attacker + " is fully healed");
    }
}
