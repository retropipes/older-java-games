/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.tallertower.maze;

import com.puttysoftware.llds.LowLevelObjectDataStore;

class LowLevelNoteDataStore extends LowLevelObjectDataStore {
    // Constructor
    LowLevelNoteDataStore(final int... shape) {
        super(shape);
    }

    // Methods
    public MazeNote getNote(final int... loc) {
        return (MazeNote) this.getCell(loc);
    }

    public void setNote(final MazeNote obj, final int... loc) {
        this.setCell(obj, loc);
    }
}
