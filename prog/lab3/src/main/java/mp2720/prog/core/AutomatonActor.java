package mp2720.prog.core;

public abstract class AutomatonActor implements Actor {
    protected static class EndStateAppliedException extends Exception {
        @Override
        public String getMessage() {
            return "cannot call apply() on the last END state";
        }
    }

    @FunctionalInterface
    protected interface StateFunction {
        StateFunction apply(Scene scene) throws EndStateAppliedException;

        /**
         * Особая функция состояния, означающая конец действий автомата.
         */
        static final StateFunction END = new StateFunction() {
            @Override
            public StateFunction apply(Scene scene) throws EndStateAppliedException {
                throw new EndStateAppliedException();
            }
        };
    }

    private StateFunction current;

    protected abstract StateFunction firstState(Scene scene);

    protected AutomatonActor() {
        this.current = s -> firstState(s);
    }

    @Override
    public Result doActions(Scene scene) {
        try {
            current = current.apply(scene);
        } catch (EndStateAppliedException e) {
            return Result.FINISH;
        }
        return Result.YIELD;
    }
}
