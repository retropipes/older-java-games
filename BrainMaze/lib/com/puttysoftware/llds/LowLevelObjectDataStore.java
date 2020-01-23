package com.puttysoftware.llds;

public class LowLevelObjectDataStore implements Cloneable {
    // Fields
    private final CloneableObject[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public LowLevelObjectDataStore(final int... shape) {
        this.dataShape = shape;
        this.interProd = new int[shape.length];
        int product = 1;
        for (int x = 0; x < shape.length; x++) {
            this.interProd[x] = product;
            product *= shape[x];
        }
        this.dataStore = new CloneableObject[product];
    }

    // Methods
    private int ravelLocation(final int... loc) {
        int res = 0;
        // Sanity check #1
        if (loc.length != this.interProd.length) {
            throw new IllegalArgumentException(Integer.toString(loc.length));
        }
        for (int x = 0; x < this.interProd.length; x++) {
            // Sanity check #2
            if (loc[x] < 0 || loc[x] >= this.dataShape[x]) {
                throw new ArrayIndexOutOfBoundsException(loc[x]);
            }
            res += loc[x] * this.interProd[x];
        }
        return res;
    }

    @Override
    public Object clone() {
        final LowLevelObjectDataStore copy = new LowLevelObjectDataStore(
                this.dataShape);
        for (int x = 0; x < copy.dataStore.length; x++) {
            copy.dataStore[x] = (CloneableObject) this.dataStore[x].clone();
        }
        return copy;
    }

    public int[] getShape() {
        return this.dataShape;
    }

    public CloneableObject getCell(final int... loc) {
        final int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(final CloneableObject obj, final int... loc) {
        final int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }
}
