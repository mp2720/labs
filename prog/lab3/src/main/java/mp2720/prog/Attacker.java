package mp2720.prog;

import mp2720.prog.core.Actor;
import mp2720.prog.core.Scene;
import mp2720.prog.core.events.Event;
import mp2720.prog.core.events.EventListener;
import mp2720.prog.events.PouredWaterOverEvent;
import mp2720.prog.events.WalkEvent;

public class Attacker implements Actor, EventListener {
    @Override
    public void onAddedToScene(Scene scene) {
        scene.registerEventListener(this);
    }

    @Override
    public void onEventOccured(Scene scene, Event event) {
        if (event instanceof WalkEvent) {
            scene.publishEvent(new PouredWaterOverEvent(this, event.getProducedBy()));
        }
    }

    @Override
    public Result doActions(Scene scene) {
        return Result.FINISH;
    }

    @Override
    public String toString() {
        return "Неизвестный";
    }
}
