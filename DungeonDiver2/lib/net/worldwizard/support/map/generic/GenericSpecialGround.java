/*  DungeonDiverII: A Map-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.worldwizard.support.map.generic;

import net.worldwizard.support.map.Map;

public abstract class GenericSpecialGround extends GenericGround {
    // Constructors
    protected GenericSpecialGround(final boolean hasFriction) {
        super(hasFriction);
    }

    @Override
    public int getMinimumRequiredQuantity(final Map map) {
        return 0;
    }

    @Override
    public int getMaximumRequiredQuantity(final Map map) {
        final int regionSizeSquared = map.getRegionSize() ^ 2;
        final int mapSize = map.getRows() * map.getColumns();
        final int regionsPerMap = mapSize / regionSizeSquared;
        return regionsPerMap / (int) Math.sqrt(mapSize);
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
