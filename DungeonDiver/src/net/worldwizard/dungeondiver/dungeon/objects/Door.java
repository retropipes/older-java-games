package net.worldwizard.dungeondiver.dungeon.objects;

import net.worldwizard.map.MapObject;
import net.worldwizard.map.NDimensionalLocation;
import net.worldwizard.map.NDimensionalMap;

public class Door extends DungeonObject {
    // Serialization
    private static final long serialVersionUID = -463406634030634L;

    // Constructors
    public Door() {
        super(false, "Door", new Wall());
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
    public boolean shouldGenerateObject(final NDimensionalLocation loc,
            final NDimensionalMap map) {
        final Tile tile = (Tile) DungeonObjectList.getSpecificObject("Tile");
        final Wall wall = (Wall) DungeonObjectList.getSpecificObject("Wall");
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
        MapObject above, below, left, right;
        try {
            above = map.getCellOffset(loc, aboveOffset);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            above = tile;
        }
        try {
            below = map.getCellOffset(loc, belowOffset);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            below = tile;
        }
        try {
            left = map.getCellOffset(loc, leftOffset);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            left = tile;
        }
        try {
            right = map.getCellOffset(loc, rightOffset);
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            right = tile;
        }
        final String aboveName = above.getName();
        final String belowName = below.getName();
        final String leftName = left.getName();
        final String rightName = right.getName();
        final boolean aboveIsWall = aboveName.equals(wall.getName());
        final boolean belowIsWall = belowName.equals(wall.getName());
        final boolean leftIsWall = leftName.equals(wall.getName());
        final boolean rightIsWall = rightName.equals(wall.getName());
        if (aboveIsWall && belowIsWall && !leftIsWall && !rightIsWall
                || !aboveIsWall && !belowIsWall && leftIsWall && rightIsWall) {
            return true;
        } else {
            return false;
        }
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
