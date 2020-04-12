/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2020 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.map;

import com.puttysoftware.mazer5d.objectmodel.ObjectModel;
import com.puttysoftware.storage.ObjectStorage;

public class Map {
    // Properties
    private final ObjectStorage data;

    // Constructors
    public Map(final int... dimensions) {
        this.data = new ObjectStorage(dimensions);
    }

    public ObjectModel getCell(final int... location) {
        return (ObjectModel) this.data.getCell(location);
    }

    public int getSize(final int dimension) {
        return this.data.getShape()[dimension];
    }

    public void setCell(final ObjectModel o, final int... location) {
        this.data.setCell(o, location);
    }

    public void fill(final ObjectModel with) {
        this.data.fill(with);
    }
}
