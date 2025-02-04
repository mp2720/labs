package mp2720.prog.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.events.Event;

public class LieInBedEvent extends Event {
    public LieInBedEvent(Actor producedBy) {
        super(producedBy);
    }

    @Override
    public String toString() {
        return String.format("%s лежит в постели", getProducedBy());
    }
}
