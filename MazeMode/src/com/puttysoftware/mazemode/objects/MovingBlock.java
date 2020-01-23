package com.puttysoftware.mazemode.objects;

import java.io.IOException;

import com.puttysoftware.mazemode.MazeMode;
import com.puttysoftware.mazemode.generic.GenericMovingObject;
import com.puttysoftware.mazemode.generic.MazeObject;
import com.puttysoftware.mazemode.generic.MazeObjectList;
import com.puttysoftware.randomnumbers.RandomRange;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class MovingBlock extends GenericMovingObject implements Cloneable {
    // Constructors
    public MovingBlock() {
        super(true);
        this.savedObject = new Empty();
        final RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public MovingBlock clone() {
        final MovingBlock copy = (MovingBlock) super.clone();
        copy.savedObject = this.savedObject.clone();
        return copy;
    }

    public void setSavedObject(final MazeObject newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the block
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        MazeMode.getApplication().getMazeManager().getMaze()
                .updateMovingBlockPosition(move, dirX, dirY, this);
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
    public int getCustomFormat() {
        return MazeObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    protected void writeMazeObjectHookX(final XDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObjectX(writer);
    }

    @Override
    protected MazeObject readMazeObjectHookX(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objectList = MazeMode.getApplication()
                .getObjects();
        this.savedObject = objectList.readMazeObjectX(reader, formatVersion);
        return this;
    }
}
