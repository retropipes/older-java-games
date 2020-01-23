/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;

class LowLevelGameObjectDataStore implements Cloneable {
    // Fields
    private final AbstractGameObject[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    public LowLevelGameObjectDataStore(final int... shape) {
        this.dataShape = shape;
        this.interProd = new int[shape.length];
        int product = 1;
        for (int x = 0; x < shape.length; x++) {
            this.interProd[x] = product;
            product *= shape[x];
        }
        this.dataStore = new AbstractGameObject[product];
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
    public LowLevelGameObjectDataStore clone() {
        final LowLevelGameObjectDataStore copy = new LowLevelGameObjectDataStore(
                this.dataShape);
        for (int x = 0; x < this.dataStore.length; x++) {
            copy.dataStore[x] = this.dataStore[x].clone();
        }
        return copy;
    }

    public int[] getShape() {
        return this.dataShape;
    }

    public AbstractGameObject getCell(final int... loc) {
        final int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    public void setCell(final AbstractGameObject obj, final int... loc) {
        final int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }
}
