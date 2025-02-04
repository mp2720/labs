package mp2720.prog;

import mp2720.prog.core.Logger;
import mp2720.prog.core.Scene;

public class Story {
    private final Scene scene;

    public Story(Logger logger) {
        scene = new Scene(logger);

        scene.addActor(new KubikInStory());
        scene.addActor(new Attacker());
    }

    public void play() {
        scene.play();
    }

    public Logger getLogger() {
        return scene.getLogger();
    }
}
