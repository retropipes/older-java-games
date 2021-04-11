package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalLocation;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;

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
        Wall fallback = new Wall();
        boolean solidAbove, solidBelow, solidLeft, solidRight;
        solidAbove = map.getCellOffset(loc, aboveOffset, fallback).isSolid();
        solidBelow = map.getCellOffset(loc, belowOffset, fallback).isSolid();
        solidLeft = map.getCellOffset(loc, leftOffset, fallback).isSolid();
        solidRight = map.getCellOffset(loc, rightOffset, fallback).isSolid();
        return !(solidAbove && solidBelow && solidLeft && solidRight);
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
