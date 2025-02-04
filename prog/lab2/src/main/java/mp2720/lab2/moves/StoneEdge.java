package mp2720.lab2.moves;

import ru.ifmo.se.pokemon.*;

public class StoneEdge extends PhysicalMove {
    public StoneEdge() {
        super(Type.ROCK, 100, 80);
    }

    @Override
    protected void applyOppEffects(Pokemon target) {
        target.setMod(Stat.SPEED, -1);
    }

    @Override
    protected double calcCriticalHit(Pokemon attacker, Pokemon target) {
        double baseProbability = attacker.getStat(Stat.SPEED) / 512.0;
        // https://bulbapedia.bulbagarden.net/wiki/Critical_hit#Core_series
        if (baseProbability * 8 > Math.random()) {
            System.out.println("Critical hit!");
            return 2.0;
        } else {
            return 1.0;
        }
    }
}
