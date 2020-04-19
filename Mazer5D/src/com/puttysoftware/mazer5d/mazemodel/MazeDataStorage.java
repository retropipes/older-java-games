package com.puttysoftware.mazer5d.mazemodel;

import java.io.IOException;
import java.util.Arrays;

import com.puttysoftware.mazer5d.files.io.XDataReader;
import com.puttysoftware.mazer5d.files.io.XDataWriter;
import com.puttysoftware.mazer5d.files.versions.MazeVersion;
import com.puttysoftware.mazer5d.objectmodel.GameObjects;
import com.puttysoftware.mazer5d.objectmodel.MazeObjectModel;

/**
 * Data storage for maze objects.
 */
public class MazeDataStorage {
    // Fields
    /**
     * The underlying array where data is stored. Exposed for serialization
     * purposes for use with the protected copy constructor.
     */
    protected final MazeObjectModel[] dataStore;
    private final int[] dataShape;
    private final int[] interProd;

    // Constructor
    /**
     * Main constructor.
     *
     * @param shape
     *            simulated dimensions for the stored data
     */
    public MazeDataStorage(final int... shape) {
        this.dataShape = shape;
        this.interProd = new int[this.dataShape.length];
        int product = 1;
        for (int x = 0; x < this.dataShape.length; x++) {
            this.interProd[x] = product;
            product *= this.dataShape[x];
        }
        this.dataStore = new MazeObjectModel[product];
    }

    // Copy constructor
    /**
     * Main copy constructor.
     *
     * @param source
     *            the @self to make a copy of
     */
    public MazeDataStorage(final MazeDataStorage source) {
        this.dataShape = source.dataShape;
        this.interProd = new int[this.dataShape.length];
        int product = 1;
        for (int x = 0; x < this.dataShape.length; x++) {
            this.interProd[x] = product;
            product *= this.dataShape[x];
        }
        this.dataStore = Arrays.copyOf(source.dataStore, product);
    }

    // Protected copy constructor
    /**
     * Serialization-related protected copy constructor.
     *
     * @param source
     *            the underlying array where stored data came from
     * @param shape
     *            simulated dimensions for the stored data
     */
    protected MazeDataStorage(final MazeObjectModel[] source,
            final int... shape) {
        this.dataShape = shape;
        this.interProd = new int[this.dataShape.length];
        int product = 1;
        for (int x = 0; x < this.dataShape.length; x++) {
            this.interProd[x] = product;
            product *= this.dataShape[x];
        }
        this.dataStore = Arrays.copyOf(source, product);
    }

    // Methods
    /**
     * Check for equality.
     *
     * @param obj
     *            the other object to check
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MazeDataStorage)) {
            return false;
        }
        final MazeDataStorage other = (MazeDataStorage) obj;
        if (!Arrays.deepEquals(this.dataStore, other.dataStore)) {
            return false;
        }
        return true;
    }

    /**
     * Fill the storage with the same data everywhere.
     *
     * @param obj
     *            the data to fill with
     */
    public final void fill(final MazeObjectModel obj) {
        for (int x = 0; x < this.dataStore.length; x++) {
            this.dataStore[x] = obj;
        }
    }

    /**
     * Get data at a given location in storage.
     *
     * @param loc
     *            the location to get data from
     * @return the data at that location
     */
    public final MazeObjectModel getCell(final int... loc) {
        final int aloc = this.ravelLocation(loc);
        return this.dataStore[aloc];
    }

    /**
     * Get data directly from the underlying array. To convert a simulated index
     * to a raw index, use ravelLocation().
     *
     * @param rawLoc
     *            the index within the array to get data from
     * @return the data at that index
     */
    protected final Object getRawCell(final int rawLoc) {
        return this.dataStore[rawLoc];
    }

    /**
     * Get the length of the underlying array.
     *
     * @return the underlying array length
     */
    protected final int getRawLength() {
        return this.dataStore.length;
    }

    /**
     * Get the shape (dimensions) of the storage.
     *
     * @return the shape, as an array of integers
     */
    public final int[] getShape() {
        return this.dataShape;
    }

    /**
     * Hashing support.
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        final int result = 1;
        return prime * result + Arrays.deepHashCode(this.dataStore);
    }

    /**
     * Utility to convert simulated indexes to raw indexes.
     *
     * @param loc
     *            a simulated index
     * @return a raw index
     */
    protected final int ravelLocation(final int... loc) {
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

    /**
     * Change stored data at a given location.
     *
     * @param obj
     *            the new data value
     * @param loc
     *            the location to modify
     */
    public final void setCell(final MazeObjectModel obj, final int... loc) {
        final int aloc = this.ravelLocation(loc);
        this.dataStore[aloc] = obj;
    }

    /**
     * Change stored data directly in the underlying array. To convert a
     * simulated index to a raw index, use ravelLocation().
     *
     * @param obj
     *            the new data value
     * @param rawLoc
     *            the index to modify
     */
    protected final void setRawCell(final MazeObjectModel obj,
            final int rawLoc) {
        this.dataStore[rawLoc] = obj;
    }

    /**
     * Dumps the state of this object to a file.
     *
     * @param writer
     *            the file writer
     * @throws IOException
     *             if an I/O error occurs
     */
    public final void dumpState(final XDataWriter writer) throws IOException {
        final int slen = this.dataShape.length;
        writer.writeInt(slen);
        for (int s = 0; s < slen; s++) {
            writer.writeInt(this.dataShape[s]);
        }
        final int dlen = this.getRawLength();
        for (int i = 0; i < dlen; i++) {
            this.dataStore[i].dumpState(writer);
        }
    }

    /**
     * Loads the state of a previously dumped object.
     *
     * @param reader
     *            the file reader
     * @param formatVersion
     *            the version of the data to load
     * @return the loaded data
     * @throws IOException
     *             if an I/O error occurs
     */
    public static MazeDataStorage loadState(final XDataReader reader,
            final MazeVersion formatVersion) throws IOException {
        final int slen = reader.readInt();
        final int[] shape = new int[slen];
        for (int s = 0; s < slen; s++) {
            shape[s] = reader.readInt();
        }
        final MazeDataStorage loaded = new MazeDataStorage(shape);
        final int dlen = loaded.getRawLength();
        for (int i = 0; i < dlen; i++) {
            loaded.setRawCell(GameObjects.readObject(reader, formatVersion), i);
        }
        return loaded;
    }
}
