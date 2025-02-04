package mp2720.prog.items;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import mp2720.prog.core.Visible;

public class Horns extends Item {
    private final Visible between;

    public Horns(Color color, Visible between) {
        super("рожки", color, Material.PLASTIC);
        this.between = between;
    }

    public Horns(Color color) {
        this(color, null);
    }

    @Override
    public List<Visible> getCompounds() {
        if (between == null)
            return Collections.emptyList();
        else
            return Arrays.asList(between);
    }
}
