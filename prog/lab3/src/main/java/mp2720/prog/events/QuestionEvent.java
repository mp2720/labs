package mp2720.prog.events;

import mp2720.prog.core.Actor;

public class QuestionEvent extends TalkEvent {
    private final Actor intendedTo;

    public QuestionEvent(Actor producedBy, String words, Actor intendedTo) {
        super(producedBy, words + "?");
        this.intendedTo = intendedTo;
    }

    public Actor getIntendedTo() {
        return intendedTo;
    }

    @Override
    public String toString() {
        return String.format("%s спрашивает %s: %s", getProducedBy(), getIntendedTo(), getWords());
    }
}
