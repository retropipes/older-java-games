package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalLocation;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;

public class Darkness extends DungeonObject {
    // Serialization
    private static final long serialVersionUID = -2346743530263425034L;

    // Constructors
    public Darkness() {
        super(false, "Darkness", null);
    }

    @Override
    public boolean shouldGenerateObject(final NDimensionalLocation loc,
            final NDimensionalMap map) {
        return false;
    }

    @Override
    public int getMinimumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public int getMaximumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public boolean isRequired() {
        return false;
    }

    @Override
    public void moveOntoHook() {
        // Do nothing
    }
}
