/*  DungeonDiver3: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver3.support.map;

import com.puttysoftware.dungeondiver3.support.map.generic.MapObject;
import com.puttysoftware.llds.LowLevelObjectDataStore;

class LowLevelDataStore extends LowLevelObjectDataStore {
    // Constructor
    LowLevelDataStore(final int... shape) {
        super(shape);
    }

    // Methods
    public MapObject getMapCell(final int... loc) {
        return (MapObject) this.getCell(loc);
    }

    public void setMapCell(final MapObject obj, final int... loc) {
        this.setCell(obj, loc);
    }
}
