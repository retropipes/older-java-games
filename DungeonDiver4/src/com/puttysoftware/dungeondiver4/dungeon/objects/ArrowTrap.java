/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrap;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class ArrowTrap extends AbstractTrap {
    // Constructors
    public ArrowTrap() {
        super(ColorConstants.COLOR_ORANGE,
                ObjectImageConstants.OBJECT_IMAGE_ARROW,
                ColorConstants.COLOR_BROWN);
    }

    @Override
    public String getName() {
        return "Arrow Trap";
    }

    @Override
    public String getPluralName() {
        return "Arrow Traps";
    }

    @Override
    public void postMoveAction(boolean ie, int dirX, int dirY,
            DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
    }

    @Override
    public boolean arrowHitAction(int locX, int locY, int locZ, int dirX,
            int dirY, int arrowType, DungeonObjectInventory inv) {
        DungeonDiver4.getApplication().showMessage("The arrow is stopped!");
        return false;
    }

    @Override
    public String getDescription() {
        return "Arrow Traps stop arrows.";
    }
}