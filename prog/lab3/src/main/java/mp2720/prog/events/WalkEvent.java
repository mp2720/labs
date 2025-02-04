package mp2720.prog.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.events.Event;

public class WalkEvent extends Event {
    private final String destination;

    public WalkEvent(Actor producedBy, String destination) {
        super(producedBy);
        this.destination = destination;
    }

    public WalkEvent(Actor producedBy) {
        this(producedBy, null);
    }

    @Override
    public String toString() {
        if (destination == null)
            return String.format("%s идёт", getProducedBy());
        else
            return String.format("%s идёт к %s", getProducedBy(), destination);
    }
}
