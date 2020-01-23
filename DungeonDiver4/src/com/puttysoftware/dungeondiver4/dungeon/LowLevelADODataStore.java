package com.puttysoftware.dungeondiver4.dungeon;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;

class LowLevelADODataStore implements Cloneable {
    // Fields
    private final AbstractDungeonObject[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public LowLevelADODataStore(int... shape) {
        this.dataShape = shape;
        this.interProd = new int[shape.length];
        int product = 1;
        for (int x = 0; x < shape.length; x++) {
            this.interProd[x] = product;
            product *= shape[x];
        }
        this.dataStore = new AbstractDungeonObject[product];
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
        LowLevelADODataStore copy = new LowLevelADODataStore(this.dataShape);
        for (int x = 0; x < this.dataStore.length; x++) {
            copy.dataStore[x] = this.dataStore[x].clone();
        }
        return copy;
    }

    public int[] getShape() {
        return this.dataShape;
    }

    public AbstractDungeonObject getCell(int... loc) {
        int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(AbstractDungeonObject obj, int... loc) {
        int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }
}
