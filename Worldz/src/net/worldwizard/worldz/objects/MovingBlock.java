package net.worldwizard.worldz.objects;

import java.io.IOException;

import net.worldwizard.io.DataReader;
import net.worldwizard.io.DataWriter;
import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericDungeonObject;
import net.worldwizard.worldz.generic.WorldObject;
import net.worldwizard.worldz.generic.WorldObjectList;

public class MovingBlock extends GenericDungeonObject implements Cloneable {
    // Fields
    private WorldObject savedObject;

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

    public WorldObject getSavedObject() {
        return this.savedObject;
    }

    public void setSavedObject(final WorldObject newSavedObject) {
        this.savedObject = newSavedObject;
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the block
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        Worldz.getApplication().getWorldManager().getWorld()
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
        return WorldObject.CUSTOM_FORMAT_MANUAL_OVERRIDE;
    }

    @Override
    protected void writeWorldObjectHook(final DataWriter writer)
            throws IOException {
        this.savedObject.writeWorldObject(writer);
    }

    @Override
    protected WorldObject readWorldObjectHook(final DataReader reader,
            final int formatVersion) throws IOException {
        final WorldObjectList objectList = Worldz.getApplication().getObjects();
        this.savedObject = objectList.readWorldObject(reader, formatVersion);
        return this;
    }
}
