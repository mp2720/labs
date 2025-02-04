package mp2720.prog.items;

public enum Color {
    RED("красный"),
    GREEN("зелёный"),
    BLUE("синий"),
    YELLOW("жёлтый"),
    WHITE("белый"),
    BLACK("чёрный"),
    PURPLE("фиолетовый");

    private final String name;

    private Color(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
