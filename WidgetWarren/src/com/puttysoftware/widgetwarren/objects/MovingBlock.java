package com.puttysoftware.widgetwarren.objects;

import java.io.IOException;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.generic.GenericMovingObject;
import com.puttysoftware.widgetwarren.generic.MazeObject;
import com.puttysoftware.widgetwarren.generic.MazeObjectList;
import com.puttysoftware.xio.XDataReader;
import com.puttysoftware.xio.XDataWriter;

public class MovingBlock extends GenericMovingObject implements Cloneable {
    // Constructors
    public MovingBlock() {
        super(true);
        this.setSavedObject(new Empty());
        final RandomRange t = new RandomRange(1, 2);
        this.activateTimer(t.generate());
    }

    @Override
    public MovingBlock clone() {
        final MovingBlock copy = (MovingBlock) super.clone();
        copy.setSavedObject(this.getSavedObject().clone());
        return copy;
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the block
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        WidgetWarren.getApplication().getMazeManager().getMaze()
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
    protected void writeMazeObjectHookXML(final XDataWriter writer)
            throws IOException {
        this.getSavedObject().writeMazeObjectXML(writer);
    }

    @Override
    protected MazeObject readMazeObjectHookXML(final XDataReader reader,
            final int formatVersion) throws IOException {
        final MazeObjectList objectList = WidgetWarren.getApplication()
                .getObjects();
        this.setSavedObject(
                objectList.readMazeObjectXML(reader, formatVersion));
        return this;
    }
}
