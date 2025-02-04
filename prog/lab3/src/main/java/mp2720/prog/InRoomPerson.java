package mp2720.prog;

import mp2720.prog.core.Scene;
import mp2720.prog.core.Actor;
import mp2720.prog.core.Visible;
import mp2720.prog.core.events.Event;
import mp2720.prog.core.events.VisibilityChangedEvent;
import mp2720.prog.core.events.EventListener;
import mp2720.prog.events.WalkEvent;
import mp2720.prog.events.QuestionEvent;

public class InRoomPerson extends Human implements Visible, EventListener {
    public InRoomPerson(String name) {
        super(name);
    }

    @Override
    public void onAddedToScene(Scene scene) {
        scene.registerVisibleActor(this, true);
        scene.registerEventListener(this);
    }

    private Actor seenKubik = null;

    @Override
    public void onEventOccured(Scene scene, Event event) {
        if (event instanceof VisibilityChangedEvent && event.getProducedBy() instanceof Kubik) {
            seenKubik = event.getProducedBy();
        }
    }

    @Override
    protected StateFunction firstState(Scene scene) {
        return goToDoor1(scene);
    }

    private final StateFunction goToDoor1(Scene scene) {
        scene.publishEvent(new WalkEvent(this, "дверь"));
        return this::waitBeforeExit;
    }

    private final StateFunction waitBeforeExit(Scene scene) {
        return this::exitRoom;
    }

    private final StateFunction exitRoom(Scene scene) {
        if (seenKubik != null) {
            return this::askKubik;
        } else {
            // disappear
            scene.unregisterVisible(this);
            return StateFunction.END;
        }
    }

    private final StateFunction askKubik(Scene scene) {
        scene.publishEvent(new QuestionEvent(this, "почему ты так долго не приходил", seenKubik));
        return StateFunction.END;
    }
}
