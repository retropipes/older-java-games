package studio.ignitionigloogames.dungeondiver1.dungeon.objects;

import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalLocation;
import studio.ignitionigloogames.dungeondiver1.utilities.NDimensionalMap;

public abstract class GenericRONSObject extends DungeonObject {
    // Serialization
    private static final long serialVersionUID = 403448605235302L;

    public GenericRONSObject(final boolean solid, final String name) {
        super(solid, name, null);
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
        return 1;
    }

    @Override
    public int getMinimumRequiredQuantity(final NDimensionalMap map) {
        return 1;
    }

    @Override
    public boolean isRequired() {
        return true;
    }
}
