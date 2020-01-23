/* Import2: An RPG */
package studio.ignitionigloogames.twistedtrek.import2.maze;

import studio.ignitionigloogames.llds.LowLevelObjectDataStore;

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
