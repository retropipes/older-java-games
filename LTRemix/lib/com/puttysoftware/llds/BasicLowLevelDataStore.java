package com.puttysoftware.llds;

import java.util.Arrays;

public class BasicLowLevelDataStore {
    // Fields
    private final Object[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public BasicLowLevelDataStore(int... shape) {
        this.dataShape = shape;
        this.interProd = new int[shape.length];
        int product = 1;
        for (int x = 0; x < shape.length; x++) {
            this.interProd[x] = product;
            product *= shape[x];
        }
        this.dataStore = new Object[product];
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

    public int[] getShape() {
        return this.dataShape;
    }

    protected Object getRawCell(int rawLoc) {
        return this.dataStore[rawLoc];
    }

    protected void setRawCell(Object cobj, int rawLoc) {
        this.dataStore[rawLoc] = cobj;
    }

    protected int getRawLength() {
        return this.dataStore.length;
    }

    public Object getCell(int... loc) {
        int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(Object obj, int... loc) {
        int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        return prime * result + Arrays.hashCode(this.dataStore);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof BasicLowLevelDataStore)) {
            return false;
        }
        BasicLowLevelDataStore other = (BasicLowLevelDataStore) obj;
        if (!Arrays.equals(this.dataStore, other.dataStore)) {
            return false;
        }
        return true;
    }
}
