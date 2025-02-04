package mp2720.prog.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.events.SoundEvent;

public class DoorKnockEvent extends SoundEvent {
    public DoorKnockEvent(Actor producedBy) {
        super(producedBy);
    }

    @Override
    public String toString() {
        return String.format("%s стучит в дверь", getProducedBy());
    }
}
