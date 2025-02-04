package mp2720.prog.core;

public class Logger {
    private final StringBuilder output = new StringBuilder();
    private final int indentLevel;

    private final String INDENT = "\t";

    public Logger(int indentLevel) {
        this.indentLevel = indentLevel;
    }

    public Logger() {
        this(0);
    }

    public Logger makeChild() {
        return new Logger(indentLevel + 1);
    }

    public void write(Object obj) {
        output.append(INDENT.repeat(indentLevel));
        output.append(obj);
        output.append('\n');
    }

    public String toString() {
        return output.toString();
    }
}
