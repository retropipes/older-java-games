/*  MazeRunnerII: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazerunner2.maze;

import com.puttysoftware.llds.LowLevelObjectDataStore;

class LowLevelNoteDataStore extends LowLevelObjectDataStore {
    // Constructor
    LowLevelNoteDataStore(int... shape) {
        super(shape);
    }

    // Methods
    public MazeNote getNote(int... loc) {
        return (MazeNote) this.getCell(loc);
    }

    public void setNote(MazeNote obj, int... loc) {
        this.setCell(obj, loc);
    }
}
