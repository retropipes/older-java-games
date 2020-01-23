/*  Risky Rescue: A Roguelike RPG
 Copyright (C) 2013-2014 Eric Ahnell

 Any questions should be directed to the author via email at: support@puttysoftware.com
 */
package com.puttysoftware.riskyrescue.map;

import com.puttysoftware.storage.ObjectStorage;

class LowLevelNoteDataStore extends ObjectStorage {
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
