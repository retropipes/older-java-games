/*  Mazer5D: A Maze-Solving Game
 Copyright (C) 2008-2010 Eric Ahnell

 Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.riskyrescue.map.objects;

import com.puttysoftware.riskyrescue.assets.ObjectImage;
import com.puttysoftware.riskyrescue.map.MapConstants;

public abstract class Arrow extends MapObject {
    // Subclasses
    private static class ArrowE extends Arrow {
        // Constructors
        public ArrowE() {
            super(ObjectImage.ARROW_EAST);
        }
    }

    private static class ArrowN extends Arrow {
        // Constructors
        public ArrowN() {
            super(ObjectImage.ARROW_NORTH);
        }
    }

    private static class ArrowNE extends Arrow {
        // Constructors
        public ArrowNE() {
            super(ObjectImage.ARROW_NORTHEAST);
        }
    }

    private static class ArrowNW extends Arrow {
        // Constructors
        public ArrowNW() {
            super(ObjectImage.ARROW_NORTHWEST);
        }
    }

    private static class ArrowS extends Arrow {
        // Constructors
        public ArrowS() {
            super(ObjectImage.ARROW_SOUTH);
        }
    }

    private static class ArrowSE extends Arrow {
        // Constructors
        public ArrowSE() {
            super(ObjectImage.ARROW_SOUTHEAST);
        }
    }

    private static class ArrowSW extends Arrow {
        // Constructors
        public ArrowSW() {
            super(ObjectImage.ARROW_SOUTHWEST);
        }
    }

    private static class ArrowW extends Arrow {
        // Constructors
        public ArrowW() {
            super(ObjectImage.ARROW_WEST);
        }
    }

    // Factory
    public static Arrow createArrow(final int dirX, final int dirY) {
        final int fdX = (int) Math.signum(dirX);
        final int fdY = (int) Math.signum(dirY);
        if (fdX == 0 && fdY == -1) {
            return new ArrowN();
        } else if (fdX == 0 && fdY == 1) {
            return new ArrowS();
        } else if (fdX == -1 && fdY == 0) {
            return new ArrowW();
        } else if (fdX == 1 && fdY == 0) {
            return new ArrowE();
        } else if (fdX == 1 && fdY == 1) {
            return new ArrowSE();
        } else if (fdX == -1 && fdY == 1) {
            return new ArrowSW();
        } else if (fdX == -1 && fdY == -1) {
            return new ArrowNW();
        } else if (fdX == 1 && fdY == -1) {
            return new ArrowNE();
        } else {
            return null;
        }
    }

    // Constructors
    protected Arrow(final ObjectImage oi) {
        super(oi, true, false);
    }

    @Override
    public final String getName() {
        return "Arrow";
    }

    @Override
    public String getPluralName() {
        return "Arrows";
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public int getLayer() {
        return MapConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MapObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}
