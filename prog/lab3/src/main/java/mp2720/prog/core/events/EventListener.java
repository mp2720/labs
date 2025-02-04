package mp2720.prog.core.events;

import mp2720.prog.core.Scene;

public interface EventListener {
    void onEventOccured(Scene scene, Event event);
}
