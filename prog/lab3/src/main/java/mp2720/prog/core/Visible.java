package mp2720.prog.core;

import java.util.Collections;
import java.util.List;

public interface Visible {
    default List<Visible> getCompounds() {
        return Collections.emptyList();
    };

    default String getDescription() {
        return toString();
    }
}
