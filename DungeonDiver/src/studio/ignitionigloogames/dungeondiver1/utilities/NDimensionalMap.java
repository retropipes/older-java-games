package studio.ignitionigloogames.dungeondiver1.utilities;

import java.awt.Point;
import java.io.Serializable;

public interface NDimensionalMap extends Serializable {
    // Constants
    int ROW_DIMENSION = 1;
    int COLUMN_DIMENSION = 0;
    int FLOOR_DIMENSION = 2;
    int LEVEL_DIMENSION = 3;
    int EXTRA_DIMENSION = 4;

    // Methods
    MapObject getCell(final NDimensionalLocation loc);

    MapObject getCellOffset(final NDimensionalLocation loc,
            final NDimensionalLocation offset, MapObject defaultValue);

    NDimensionalLocation getStart(final int dimension);

    NDimensionalLocation getDimensions();

    int getMaxDimension();

    boolean findObject(final MapObject obj);

    boolean findNthObject(final int N, final MapObject obj);

    boolean findObjectOnDimension(final int dimension, final MapObject obj);

    boolean findNthObjectOnDimension(final int dimension, final int N,
            final MapObject obj);

    NDimensionalLocation getFindResult();

    void setCell(final MapObject mo, final NDimensionalLocation loc);

    void setStart(final int dimension, final NDimensionalLocation newStart);

    void save();

    void saveDimension(final int dimension);

    void restore();

    void restoreDimension(final int dimension);

    void fillMap(final MapObject fillObj);

    void fillMapDimension(final MapObject fillObj, final int dimension);

    void fillMapRandomly(final MapObjectList objects,
            final MapObject pass1Fill);

    void fillMapDimensionRandomly(final MapObjectList objects,
            final MapObject pass1Fill, final int dimension);

    void drawGame(NDimensionalLocation otherDimensions);

    void drawGameWithVisibility(NDimensionalLocation otherDimensions);

    void drawEditor(NDimensionalLocation otherDimensions);

    void drawEditorWithVisibility(NDimensionalLocation otherDimensions);

    void drawOther(int appearanceID, NDimensionalLocation otherDimensions);

    void drawOtherWithVisibility(int appearanceID,
            NDimensionalLocation otherDimensions);

    Point alterViewingWindow(Point newViewingWindow);

    Point alterViewingWindowOffset(Point newViewingWindowOffset);

    int getHorizontalDrawDistance();

    void setHorizontalDrawDistance(int newDrawDistance);

    void incrementHorizontalDrawDistance();

    void decrementHorizontalDrawDistance();

    int getVerticalDrawDistance();

    void setVerticalDrawDistance(int newDrawDistance);

    void incrementVerticalDrawDistance();

    void decrementVerticalDrawDistance();

    int getDrawDistance();

    void setDrawDistance(int newDrawDistance);

    void incrementDrawDistance();

    void decrementDrawDistance();
}