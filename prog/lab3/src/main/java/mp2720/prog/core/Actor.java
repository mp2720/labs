package mp2720.prog.core;

public interface Actor {
    public enum Result {
        YIELD,
        FINISH
    }

    default void onAddedToScene(Scene scene) {
    }

    Result doActions(Scene scene);
}
