package studio.ignitionigloogames.dungeondiver1.utilities;

import java.io.Serializable;

public class NDimensionalLocation implements Serializable {
    // Fields
    private static final long serialVersionUID = 825235532499346L;
    private final int[] loc;

    // Constructors
    public NDimensionalLocation(final int dimensions) {
        this.loc = new int[dimensions];
        int x;
        for (x = 0; x < dimensions; x++) {
            this.loc[x] = 0;
        }
    }

    public NDimensionalLocation(final NDimensionalLocation otherLocation) {
        this.loc = otherLocation.loc;
    }

    public NDimensionalLocation(final NDimensionalLocation otherLocation,
            final NDimensionalLocation locationOffset) {
        this.loc = otherLocation.loc;
        int x;
        for (x = 0; x < otherLocation.loc.length; x++) {
            this.loc[x] += locationOffset.loc[x];
        }
    }

    // Methods
    public int getLocation(final int dimension) {
        return this.loc[dimension];
    }

    public int getDimensions() {
        return this.loc.length;
    }

    public void setLocation(final int dimension, final int newValue) {
        this.loc[dimension] = newValue;
    }

    public void offsetLocation(final int dimension, final int offset) {
        this.loc[dimension] += offset;
    }
}
