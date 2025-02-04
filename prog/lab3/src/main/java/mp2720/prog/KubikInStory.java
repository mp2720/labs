package mp2720.prog;

import mp2720.prog.core.Scene;
import mp2720.prog.core.Visible;
import mp2720.prog.core.events.Event;
import mp2720.prog.core.events.EventListener;
import mp2720.prog.events.HealthChangedEvent;
import mp2720.prog.events.LieInBedEvent;
import mp2720.prog.events.PouredWaterOverEvent;
import mp2720.prog.events.WalkEvent;

public class KubikInStory extends Human implements EventListener, Visible {
    public KubikInStory() {
        super("Кубик");
    }

    @Override
    public void onAddedToScene(Scene scene) {
        scene.registerEventListener(this);
        scene.registerVisibleActor(this);
    }

    @Override
    public void onEventOccured(Scene scene, Event event) {
        if (event instanceof PouredWaterOverEvent) {
            var pwEvent = (PouredWaterOverEvent) event;
            if (pwEvent.getVictim() == this)
                changeHealth(-60);
        }
    }

    @Override
    protected StateFunction firstState(Scene scene) {
        return walk(scene);
    }

    private StateFunction walk(Scene scene) {
        scene.publishEvent(new WalkEvent(this));
        return this::endOfWalk;
    }

    private StateFunction endOfWalk(Scene scene) {
        if (isHealthy())
            return StateFunction.END;

        scene.publishEvent(new HealthChangedEvent(this, false));
        return this::restInBed;
    }

    private StateFunction restInBed(Scene scene) {
        scene.publishEvent(new LieInBedEvent(this));
        return this::recover;
    }

    private StateFunction recover(Scene scene) {
        scene.publishEvent(new HealthChangedEvent(this, true));
        return StateFunction.END;
    }
}
