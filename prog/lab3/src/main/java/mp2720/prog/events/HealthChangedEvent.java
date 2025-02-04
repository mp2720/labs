package mp2720.prog.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.events.Event;

public class HealthChangedEvent extends Event {
    private final boolean isHealthy;

    public HealthChangedEvent(Actor producedBy, boolean isHealthy) {
        super(producedBy);
        this.isHealthy = isHealthy;
    }

    @Override
    public String toString() {
        if (isHealthy)
            return String.format("%s выздоровел и может ходить", getProducedBy());
        else
            return String.format("%s заболел", getProducedBy());
    }
}
