package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;

public class Tile extends GenericNotSurroundedObject {
    // Serialization
    private static final long serialVersionUID = -32034603396305L;

    // Constructors
    public Tile() {
        super(false, "Tile");
    }

    @Override
    public int getMaximumRequiredQuantity(final NDimensionalMap map) {
        return 0;
    }

    @Override
    public int getMinimumRequiredQuantity(final NDimensionalMap map) {
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
