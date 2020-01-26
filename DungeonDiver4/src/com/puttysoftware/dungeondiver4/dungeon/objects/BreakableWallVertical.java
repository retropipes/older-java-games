/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.dungeon.utilities.TypeConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class BreakableWallVertical extends AbstractWall {
    // Constructors
    public BreakableWallVertical() {
        super(ColorConstants.COLOR_BROWN);
        this.setAttributeID(
                ObjectImageConstants.OBJECT_IMAGE_BREAKABLE_VERTICAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public String getName() {
        return "Breakable Wall Vertical";
    }

    @Override
    public String getPluralName() {
        return "Breakable Walls Vertical";
    }

    @Override
    public String getDescription() {
        return "Breakable Walls Vertical break up into nothing if walked into, and propagate the effect to other like walls.";
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        final int dirZ = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon().getPlayerLocationZ();
        this.chainReactionAction(dirX, dirY, dirZ);
        SoundManager.playSound(SoundConstants.SOUND_CRACK);
    }

    @Override
    public void chainReactionAction(final int dirX, final int dirY,
            final int dirZ) {
        // Break up
        DungeonDiver4.getApplication().getGameManager().morph(new Empty(), dirX,
                dirY, dirZ, DungeonConstants.LAYER_OBJECT);
        final Dungeon m = DungeonDiver4.getApplication().getDungeonManager()
                .getDungeon();
        final AbstractDungeonObject above = m.getCell(dirX, dirY - 1, dirZ,
                DungeonConstants.LAYER_OBJECT);
        try {
            if (above.isOfType(TypeConstants.TYPE_BREAKABLE_V)) {
                this.chainReactionAction(dirX, dirY - 1, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
        try {
            final AbstractDungeonObject below = m.getCell(dirX, dirY + 1, dirZ,
                    DungeonConstants.LAYER_OBJECT);
            if (below.isOfType(TypeConstants.TYPE_BREAKABLE_V)) {
                this.chainReactionAction(dirX, dirY + 1, dirZ);
            }
        } catch (final ArrayIndexOutOfBoundsException aioobe) {
            // Ignore
        }
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_WALL);
        this.type.set(TypeConstants.TYPE_BREAKABLE_V);
    }
}