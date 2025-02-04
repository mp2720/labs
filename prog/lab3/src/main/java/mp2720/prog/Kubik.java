package mp2720.prog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import mp2720.prog.core.Scene;
import mp2720.prog.core.Visible;
import mp2720.prog.core.events.Event;
import mp2720.prog.core.events.EventListener;
import mp2720.prog.events.QuestionEvent;
import mp2720.prog.events.TalkEvent;
import mp2720.prog.items.Color;
import mp2720.prog.items.Hat;
import mp2720.prog.items.Horns;
import mp2720.prog.items.Material;
import mp2720.prog.items.Megaphone;
import mp2720.prog.items.MetalBox;
import mp2720.prog.items.SpiralWire;
import mp2720.prog.events.DoorKnockEvent;

public class Kubik extends Human implements Visible, EventListener {
    public Kubik() {
        super("Кубик");
    }

    @Override
    public List<Visible> getCompounds() {
        return new ArrayList<Visible>(Arrays.asList(
                new Hat(Color.RED, Material.PLASTIC),
                new MetalBox(Color.BLUE),
                new MetalBox(Color.RED, new Megaphone(Color.GREEN)),
                new Horns(Color.BLACK, new SpiralWire(Color.RED))));
    }

    @Override
    public void onAddedToScene(Scene scene) {
        scene.registerEventListener(this);
    }

    private boolean heardQuestion = false;

    @Override
    public void onEventOccured(Scene scene, Event event) {
        if (event instanceof QuestionEvent) {
            var question = (QuestionEvent) event;
            if (question.getIntendedTo() == this)
                heardQuestion = true;
        }
    }

    @Override
    protected StateFunction firstState(Scene scene) {
        return knockDoor(scene);
    }

    private StateFunction knockDoor(Scene scene) {
        scene.publishEvent(new DoorKnockEvent(this));
        return this::enterRoom;
    }

    private StateFunction enterRoom(Scene scene) {
        scene.registerVisibleActor(this);
        return this::waitForQuestion;
    }

    private StateFunction waitForQuestion(Scene scene) {
        return this::answerQuestions;
    }

    private StateFunction answerQuestions(Scene scene) {
        if (heardQuestion) {
            var story = new Story(scene.getLogger().makeChild());
            story.play();
            scene.publishEvent(new TalkEvent(this, "\n" + story.getLogger().toString()));
        }

        return StateFunction.END;
    }
}
