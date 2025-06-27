/*  LLDS: Arbitrary dimension arrays for Java programs
Licensed under Apache 2.0. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/wrldwzrd89/lib-java-low-level-data-storage
 */
package studio.ignitionigloogames.llds;

public final class LowLevelLongDataStore implements Cloneable {
	// Fields
	private final long[] dataStore;
	private final int[] dataShape;
	private final int[] interProd;

	// Constructor
	public LowLevelLongDataStore(final int... shape) {
		this.dataShape = shape;
		this.interProd = new int[shape.length];
		int product = 1;
		for (int x = 0; x < shape.length; x++) {
			this.interProd[x] = product;
			product *= shape[x];
		}
		this.dataStore = new long[product];
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
		final LowLevelLongDataStore copy = new LowLevelLongDataStore(this.dataShape);
		System.arraycopy(this.dataStore, 0, copy.dataStore, 0, this.dataStore.length);
		return copy;
	}

	public int[] getShape() {
		return this.dataShape;
	}

	public long getCell(final int... loc) {
		final int aloc = this.ravelLocation(loc);
		return this.dataStore[aloc];
	}

	public void setCell(final long obj, final int... loc) {
		final int aloc = this.ravelLocation(loc);
		this.dataStore[aloc] = obj;
	}

	public void fill(final long obj) {
		for (int x = 0; x < this.dataStore.length; x++) {
			this.dataStore[x] = obj;
		}
	}
}
