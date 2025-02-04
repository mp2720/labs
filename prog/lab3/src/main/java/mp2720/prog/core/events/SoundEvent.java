package mp2720.prog.core.events;

import mp2720.prog.core.Actor;

public abstract class SoundEvent extends Event {
    public SoundEvent(Actor producedBy) {
        super(producedBy);
    }
}
