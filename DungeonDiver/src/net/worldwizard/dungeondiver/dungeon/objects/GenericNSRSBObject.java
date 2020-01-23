package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.map.NDimensionalLocation;
import net.worldwizard.map.NDimensionalMap;

public abstract class GenericNSRSBObject extends DungeonObject {
    // Serialization
    private static final long serialVersionUID = 34034060639202357L; // Fields
    private final int sizeFactor;

    public GenericNSRSBObject(final boolean solid, final String name,
            final int newSizeFactor) {
        super(solid, name, null);
        this.sizeFactor = newSizeFactor;
    }

    @Override
    public boolean shouldGenerateObject(final NDimensionalLocation loc,
            final NDimensionalMap map) {
        final NDimensionalLocation aboveOffset = new NDimensionalLocation(
                map.getMaxDimension());
        final NDimensionalLocation belowOffset = new NDimensionalLocation(
                map.getMaxDimension());
        final NDimensionalLocation leftOffset = new NDimensionalLocation(
                map.getMaxDimension());
        final NDimensionalLocation rightOffset = new NDimensionalLocation(
                map.getMaxDimension());
        aboveOffset.setLocation(NDimensionalMap.ROW_DIMENSION, -1);
        aboveOffset.setLocation(NDimensionalMap.COLUMN_DIMENSION, 0);
        belowOffset.setLocation(NDimensionalMap.ROW_DIMENSION, 1);
        belowOffset.setLocation(NDimensionalMap.COLUMN_DIMENSION, 0);
        leftOffset.setLocation(NDimensionalMap.ROW_DIMENSION, 0);
        leftOffset.setLocation(NDimensionalMap.COLUMN_DIMENSION, -1);
        rightOffset.setLocation(NDimensionalMap.ROW_DIMENSION, 0);
        rightOffset.setLocation(NDimensionalMap.COLUMN_DIMENSION, 1);
        boolean solidAbove, solidBelow, solidLeft, solidRight;
        try {
            solidAbove = map.getCellOffset(loc, aboveOffset).isSolid();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            solidAbove = true;
        }
        try {
            solidBelow = map.getCellOffset(loc, belowOffset).isSolid();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            solidBelow = true;
        }
        try {
            solidLeft = map.getCellOffset(loc, leftOffset).isSolid();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            solidLeft = true;
        }
        try {
            solidRight = map.getCellOffset(loc, rightOffset).isSolid();
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            solidRight = true;
        }
        if (solidAbove && solidBelow && solidLeft && solidRight) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public int getMaximumRequiredQuantity(final NDimensionalMap map) {
        final NDimensionalLocation size = map.getDimensions();
        final int maxDimension = map.getMaxDimension();
        int x;
        int product = 1;
        for (x = 0; x < maxDimension; x++) {
            product *= size.getLocation(x);
        }
        return product / this.sizeFactor;
    }

    @Override
    public int getMinimumRequiredQuantity(final NDimensionalMap map) {
        return this.getMaximumRequiredQuantity(map);
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
