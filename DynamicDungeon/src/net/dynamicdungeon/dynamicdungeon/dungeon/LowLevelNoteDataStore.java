/*  DynamicDungeon: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package net.dynamicdungeon.dynamicdungeon.dungeon;

import net.dynamicdungeon.llds.LowLevelObjectDataStore;

class LowLevelNoteDataStore extends LowLevelObjectDataStore {
    // Constructor
    LowLevelNoteDataStore(final int... shape) {
        super(shape);
    }

    // Methods
    public DungeonNote getNote(final int... loc) {
        return (DungeonNote) this.getCell(loc);
    }

    public void setNote(final DungeonNote obj, final int... loc) {
        this.setCell(obj, loc);
    }
}
