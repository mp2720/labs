package mp2720.prog;

import mp2720.prog.core.AutomatonActor;

public abstract class Human extends AutomatonActor {
    private final String name;
    private int health = 100;

    public Human(String name) {
        this.name = name;
    }

    public void changeHealth(int d) {
        // 0 <= health <= 100
        health = Math.max(0, Math.min(health + d, 100));
    }

    public boolean isHealthy() {
        return health > 60;
    }

    @Override
    public String toString() {
        return name;
    }
}
