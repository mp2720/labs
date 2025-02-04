package mp2720.prog.core;

import java.util.ArrayList;

public class Viewer {
    public String view(Visible visible) {
        var compoundsStr = new ArrayList<String>();

        for (var compound : visible.getCompounds()) {
            compoundsStr.add(view(compound));
        }

        if (compoundsStr.isEmpty())
            return visible.getDescription();
        else
            return visible.getDescription() + " с " + String.join(", ", compoundsStr);
    }
}
