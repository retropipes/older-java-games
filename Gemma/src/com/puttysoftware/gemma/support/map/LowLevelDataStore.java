/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map;

import com.puttysoftware.gemma.support.map.generic.MapObject;
import com.puttysoftware.storage.ObjectStorage;

class LowLevelDataStore extends ObjectStorage {
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
