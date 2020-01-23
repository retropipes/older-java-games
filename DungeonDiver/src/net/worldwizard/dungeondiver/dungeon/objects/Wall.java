package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.map.NDimensionalLocation;
import net.worldwizard.map.NDimensionalMap;
import net.worldwizard.randomnumbers.RandomRange;

public class Wall extends DungeonObject {
    // Serialization
    private static final long serialVersionUID = -9329235259329620L;

    // Constructors
    public Wall() {
        super(true, "Wall", null);
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
    public boolean shouldGenerateObject(final NDimensionalLocation loc,
            final NDimensionalMap map) {
        // Generate 75% of possible walls
        final RandomRange r = new RandomRange(1, 4);
        final int allowed = (int) r.generate();
        if (allowed == 4) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void moveOntoHook() {
        // Do nothing
    }
}
