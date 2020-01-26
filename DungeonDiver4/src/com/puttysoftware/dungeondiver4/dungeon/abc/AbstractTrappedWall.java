/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: dungeonr5d@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.abc;

import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public abstract class AbstractTrappedWall extends AbstractWall {
    // Fields
    private int number;
    protected static final int NUMBER_MASTER = -1;

    // Constructors
    protected AbstractTrappedWall(final int newNumber) {
        super(ColorConstants.COLOR_BROWN);
        this.number = newNumber;
    }

    @Override
    public AbstractTrappedWall clone() {
        final AbstractTrappedWall copy = (AbstractTrappedWall) super.clone();
        copy.number = this.number;
        return copy;
    }

    @Override
    public abstract int getAttributeID();

    @Override
    public int getGameAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public String getName() {
        if (this.number == AbstractTrappedWall.NUMBER_MASTER) {
            return "Master Trapped Wall";
        } else {
            return "Trapped Wall " + this.number;
        }
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        if (this.number == AbstractTrappedWall.NUMBER_MASTER) {
            return "Master Trapped Walls";
        } else {
            return "Trapped Walls " + this.number;
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_TRAPPED_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Blacklist object
        return false;
    }
}