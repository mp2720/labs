package mp2720.prog.core;

import java.util.ArrayList;
import mp2720.prog.core.events.Event;
import mp2720.prog.core.events.EventListener;
import mp2720.prog.core.events.VisibilityChangedEvent;

public class Scene {
    private final ArrayList<EventListener> listeners = new ArrayList<>();
    private final ArrayList<Visible> visibles = new ArrayList<>();
    private final ArrayList<Actor> actors = new ArrayList<>();
    private final Logger logger;

    public Scene(Logger logger) {
        this.logger = logger;
    }

    public Logger getLogger() {
        return logger;
    }

    public void publishEvent(Event event, boolean silent) {
        if (!silent)
            logger.write(event);

        for (var listener : listeners)
            listener.onEventOccured(this, event);
    }

    public void publishEvent(Event event) {
        publishEvent(event, false);
    }

    public void registerEventListener(EventListener listener) {
        listeners.add(listener);
    }

    public <T extends Actor & Visible> void registerVisibleActor(T visibleActor, boolean silent) {
        visibles.add(visibleActor);
        publishEvent(new VisibilityChangedEvent(visibleActor, true), silent);
    }

    public <T extends Actor & Visible> void registerVisibleActor(T visibleActor) {
        registerVisibleActor(visibleActor, false);
    }

    public <T extends Actor & Visible> void unregisterVisible(T visibleActor) {
        visibles.remove((Visible) visibleActor);
        publishEvent(new VisibilityChangedEvent(visibleActor, false));
    }

    public void addActor(Actor actor) {
        actors.add(actor);
        actor.onAddedToScene(this);
    }

    public void play() {
        boolean hasUnfinishedActors;
        while (true) {
            hasUnfinishedActors = false;
            for (var actor : actors)
                if (actor.doActions(this) != Actor.Result.FINISH)
                    hasUnfinishedActors = true;
                

            if (!hasUnfinishedActors)
                break;
        }
    }
}
