package mp2720.prog.items;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mp2720.prog.core.Visible;

public class MetalBox extends Item {
    private final Visible inside;

    public MetalBox(Color color, Visible inside) {
        super("коробка", color, Material.METAL);
        this.inside = inside;
    }

    public MetalBox(Color color) {
        this(color, null);
    }

    @Override
    public List<Visible> getCompounds() {
        if (inside == null)
            return Collections.emptyList();
        else
            return Arrays.asList(inside);
    }
}
