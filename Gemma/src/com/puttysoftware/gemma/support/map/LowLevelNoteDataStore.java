/*  Gemma: An RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.gemma.support.map;

import com.puttysoftware.llds.LowLevelObjectDataStore;

class LowLevelNoteDataStore extends LowLevelObjectDataStore {
    // Constructor
    LowLevelNoteDataStore(final int... shape) {
        super(shape);
    }

    // Methods
    public MapNote getNote(final int... loc) {
        return (MapNote) this.getCell(loc);
    }

    public void setNote(final MapNote obj, final int... loc) {
        this.setCell(obj, loc);
    }
}
