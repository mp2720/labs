package mp2720.prog;

import mp2720.prog.core.Logger;
import mp2720.prog.core.Scene;

public class Room {
    private final Scene scene;

    public Room(Logger logger) {
        scene = new Scene(logger);

        //scene.addActor(new InRoomPerson("Незнайка"));
        //scene.addActor(new InRoomPerson("Кнопочка"));
        //scene.addActor(new InRoomPerson("Пестренький"));
        scene.addActor(new Kubik());
    }

    public void play() {
        scene.play();
    }

    public Logger getLogger() {
        return scene.getLogger();
    }
}
