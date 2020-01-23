package com.puttysoftware.llds;

import java.util.Arrays;

public final class LowLevelNumberDataStore implements Cloneable {
    // Fields
    private final int[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public LowLevelNumberDataStore(int... shape) {
        this.dataShape = shape;
        this.interProd = new int[shape.length];
        int product = 1;
        for (int x = 0; x < shape.length; x++) {
            this.interProd[x] = product;
            product *= shape[x];
        }
        this.dataStore = new int[product];
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
    
    public void fill(int val) {
    	Arrays.fill(this.dataStore, val);
    }

    @Override
    public Object clone() {
        LowLevelNumberDataStore copy = new LowLevelNumberDataStore(
                this.dataShape);
        System.arraycopy(this.dataStore, 0, copy.dataStore, 0,
                this.dataStore.length);
        return copy;
    }

    public int[] getShape() {
        return this.dataShape;
    }

    public int getCell(int... loc) {
        int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(int obj, int... loc) {
        int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }
}
