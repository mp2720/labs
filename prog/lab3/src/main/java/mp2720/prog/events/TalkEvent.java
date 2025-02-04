package mp2720.prog.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.events.SoundEvent;

public class TalkEvent extends SoundEvent {
    private final String words;

    public String getWords() {
        return words;
    }

    public TalkEvent(Actor producedBy, String words) {
        super(producedBy);
        this.words = words;
    }

    @Override
    public String toString() {
        return String.format("%s говорит: %s", getProducedBy(), words);
    }
}
