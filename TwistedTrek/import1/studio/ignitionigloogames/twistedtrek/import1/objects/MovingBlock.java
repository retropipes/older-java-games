/* Import1: A Maze-Solving Game */
package studio.ignitionigloogames.twistedtrek.import1.objects;

import java.io.IOException;

import studio.ignitionigloogames.randomrange.RandomRange;
import studio.ignitionigloogames.twistedtrek.import1.Import1;
import studio.ignitionigloogames.twistedtrek.import1.generic.GenericDungeonObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObject;
import studio.ignitionigloogames.twistedtrek.import1.generic.MazeObjectList;
import studio.ignitionigloogames.xio.XDataReader;
import studio.ignitionigloogames.xio.XDataWriter;

public class MovingBlock extends GenericDungeonObject {
    // Fields
    private MazeObject savedObject;

    // Constructors
    public MovingBlock() {
        super(true);
        this.savedObject = new Empty();
        final RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public MovingBlock clone() {
        final MovingBlock copy = new MovingBlock();
        copy.savedObject = this.savedObject.clone();
        return copy;
    }

    @Override
    public boolean hasAdditionalProperties() {
        return true;
    }

    public MazeObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final MazeObject newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the block
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        Import1.getApplication().getMazeManager().getMaze().updateMovingBlockPosition(move, dirX, dirY, this);
        final RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public String getName() {
        return "Moving Block";
    }

    @Override
    public String getPluralName() {
        return "Moving Blocks";
    }

    @Override
    public String getDescription() {
        return "Moving Blocks move on their own. They cannot be pushed or pulled.";
    }

    @Override
    public byte getObjectID() {
        return (byte) 0;
    }

    @Override
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    protected void writeMazeObjectHook(final XDataWriter writer) throws IOException {
        this.savedObject.writeMazeObject(writer);
    }

    @Override
    protected MazeObject readMazeObjectHook(final XDataReader reader, final int formatVersion) throws IOException {
        final MazeObjectList objectList = Import1.getApplication().getObjects();
        this.savedObject = objectList.readMazeObject(reader, formatVersion);
        return this;
    }
}
