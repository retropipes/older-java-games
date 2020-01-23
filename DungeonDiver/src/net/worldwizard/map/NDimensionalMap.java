package net.worldwizard.map;

import java.awt.Container;
import java.awt.Point;
import java.io.Serializable;

public interface NDimensionalMap extends Serializable {
    // Constants
    public static final int ROW_DIMENSION = 1;
    public static final int COLUMN_DIMENSION = 0;
    public static final int FLOOR_DIMENSION = 2;
    public static final int LEVEL_DIMENSION = 3;
    public static final int EXTRA_DIMENSION = 4;

    // Methods
    public MapObject getCell(final NDimensionalLocation loc);

    public MapObject getCellOffset(final NDimensionalLocation loc,
            final NDimensionalLocation offset);

    public NDimensionalLocation getStart(final int dimension);

    public NDimensionalLocation getDimensions();

    public int getMaxDimension();

    public boolean findObject(final MapObject obj);

    public boolean findNthObject(final int N, final MapObject obj);

    public boolean findObjectOnDimension(final int dimension,
            final MapObject obj);

    public boolean findNthObjectOnDimension(final int dimension, final int N,
            final MapObject obj);

    public NDimensionalLocation getFindResult();

    public void setCell(final MapObject mo, final NDimensionalLocation loc);

    public void setStart(final int dimension,
            final NDimensionalLocation newStart);

    public void save();

    public void saveDimension(final int dimension);

    public void restore();

    public void restoreDimension(final int dimension);

    public void fillMap(final MapObject fillObj);

    public void fillMapDimension(final MapObject fillObj, final int dimension);

    public void fillMapRandomly(final MapObjectList objects,
            final MapObject pass1Fill);

    public void fillMapDimensionRandomly(final MapObjectList objects,
            final MapObject pass1Fill, final int dimension);

    public Container drawGame(NDimensionalLocation otherDimensions);

    public Container drawGameWithVisibility(NDimensionalLocation otherDimensions);

    public Container drawEditor(NDimensionalLocation otherDimensions);

    public Container drawEditorWithVisibility(
            NDimensionalLocation otherDimensions);

    public Container drawOther(int appearanceID,
            NDimensionalLocation otherDimensions);

    public Container drawOtherWithVisibility(int appearanceID,
            NDimensionalLocation otherDimensions);

    public Point alterViewingWindow(Point newViewingWindow);

    public Point alterViewingWindowOffset(Point newViewingWindowOffset);

    public int getHorizontalDrawDistance();

    public void setHorizontalDrawDistance(int newDrawDistance);

    public void incrementHorizontalDrawDistance();

    public void decrementHorizontalDrawDistance();

    public int getVerticalDrawDistance();

    public void setVerticalDrawDistance(int newDrawDistance);

    public void incrementVerticalDrawDistance();

    public void decrementVerticalDrawDistance();

    public int getDrawDistance();

    public void setDrawDistance(int newDrawDistance);

    public void incrementDrawDistance();

    public void decrementDrawDistance();
}