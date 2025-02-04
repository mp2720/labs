package mp2720.prog.core.events;

import mp2720.prog.core.Actor;
import mp2720.prog.core.Viewer;
import mp2720.prog.core.Visible;

public final class VisibilityChangedEvent extends Event {
    private final boolean becameVisible;
    private final Visible visible;

    public <T extends Actor & Visible> VisibilityChangedEvent(T producedBy, boolean becameVisible) {
        super(producedBy);
        this.becameVisible = becameVisible;
        this.visible = producedBy;
    }

    @Override
    public String toString() {
        return String.format(
            "%s %s",
            becameVisible ? "Появился" : "Исчез",
            new Viewer().view(visible)
        );
    }
}
