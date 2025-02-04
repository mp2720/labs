package mp2720.prog.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.events.Event;

public class PouredWaterOverEvent extends Event {
    private final Actor victim;

    public Actor getVictim() {
        return victim;
    }

    public PouredWaterOverEvent(Actor producedBy, Actor victim) {
        super(producedBy);
        this.victim = victim;
    }

    @Override
    public String toString() {
        return String.format("%s облил водой %s", getProducedBy(), victim);
    }
}
