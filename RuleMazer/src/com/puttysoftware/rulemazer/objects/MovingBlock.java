package com.puttysoftware.rulemazer.objects;

import java.io.IOException;

import com.puttysoftware.randomnumbers.RandomRange;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.generic.GenericMovingObject;
import com.puttysoftware.rulemazer.generic.MazeObject;
import com.puttysoftware.rulemazer.generic.MazeObjectList;
import com.puttysoftware.xmlio.XMLDataReader;
import com.puttysoftware.xmlio.XMLDataWriter;

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
        Main.getApplication().getMazeManager().getMaze()
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
    protected void writeMazeObjectHookXML(final XMLDataWriter writer)
            throws IOException {
        this.savedObject.writeMazeObjectXML(writer);
    }

    @Override
    protected MazeObject readMazeObjectHookXML(final XMLDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objectList = Main.getApplication().getObjects();
        this.savedObject = objectList.readMazeObjectXML(reader, formatVersion);
        return this;
    }
}
