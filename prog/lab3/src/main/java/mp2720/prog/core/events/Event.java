package mp2720.prog.core.events;

import mp2720.prog.core.Actor;

public abstract class Event {
    private final Actor producedBy;

    public Event(Actor producedBy) {
        this.producedBy = producedBy;
    }

    public Actor getProducedBy() {
        return producedBy;
    }
}
