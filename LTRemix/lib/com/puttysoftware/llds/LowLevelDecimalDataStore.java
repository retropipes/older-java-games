package com.puttysoftware.llds;

public final class LowLevelDecimalDataStore implements Cloneable {
    // Fields
    private final double[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public LowLevelDecimalDataStore(int... shape) {
        this.dataShape = shape;
        this.interProd = new int[shape.length];
        int product = 1;
        for (int x = 0; x < shape.length; x++) {
            this.interProd[x] = product;
            product *= shape[x];
        }
        this.dataStore = new double[product];
    }

    // Methods
    private int ravelLocation(int... loc) {
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
            res += (loc[x] * this.interProd[x]);
        }
        return res;
    }

    @Override
    public Object clone() {
        LowLevelDecimalDataStore copy = new LowLevelDecimalDataStore(
                this.dataShape);
        System.arraycopy(this.dataStore, 0, copy.dataStore, 0,
                this.dataStore.length);
        return copy;
    }

    public int[] getShape() {
        return this.dataShape;
    }

    public double getCell(int... loc) {
        int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(double obj, int... loc) {
        int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }
}
