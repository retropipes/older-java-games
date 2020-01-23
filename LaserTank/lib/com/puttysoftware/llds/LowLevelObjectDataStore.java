package com.puttysoftware.llds;

import java.util.Arrays;

public class LowLevelObjectDataStore implements Cloneable {
    // Fields
    private final CloneableObject[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public LowLevelObjectDataStore(int... shape) {
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
    public Object clone() throws CloneNotSupportedException {
        LowLevelObjectDataStore copy = new LowLevelObjectDataStore(
                this.dataShape);
        for (int x = 0; x < copy.dataStore.length; x++) {
            if (this.dataStore[x] != null) {
                copy.dataStore[x] = (CloneableObject) this.dataStore[x].clone();
            }
        }
        return copy;
    }

    public int[] getShape() {
        return this.dataShape;
    }

    protected CloneableObject getRawCell(int rawLoc) {
        return this.dataStore[rawLoc];
    }

    protected void setRawCell(CloneableObject cobj, int rawLoc) {
        this.dataStore[rawLoc] = cobj;
    }

    protected int getRawLength() {
        return this.dataStore.length;
    }

    public CloneableObject getCell(int... loc) {
        int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(CloneableObject obj, int... loc) {
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
        if (!(obj instanceof LowLevelObjectDataStore)) {
            return false;
        }
        LowLevelObjectDataStore other = (LowLevelObjectDataStore) obj;
        if (!Arrays.equals(this.dataStore, other.dataStore)) {
            return false;
        }
        return true;
    }
}
