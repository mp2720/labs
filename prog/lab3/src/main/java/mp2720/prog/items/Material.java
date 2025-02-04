package mp2720.prog.items;

public enum Material {
    PLASTIC("пластик"),
    METAL("металл");

    private final String name;

    private Material(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
