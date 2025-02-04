package mp2720.prog.items;

import mp2720.prog.core.Visible;

public abstract class Item implements Visible {
    private final String name;
    private final Material material;
    private final Color color;

    public Material getMaterial() {
        return material;
    }

    public Color getColor() {
        return color;
    }

    public Item(String name, Color color, Material material) {
        this.name = name;
        this.color = color;
        this.material = material;
    }

    @Override
    public String toString() {
        return String.format("%s из материала %s цвета %s", name, material, color);
    }
}
