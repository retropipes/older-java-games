/*  DDRemix: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.Application;
import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.creatures.party.PartyManager;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractMazeObject;
import com.puttysoftware.ddremix.maze.abc.AbstractTeleport;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

public class StairsUp extends AbstractTeleport {
    // Constructors
    public StairsUp() {
        super(0, 0, 0);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAIRS_UP;
    }

    @Override
    public String getName() {
        return "Stairs Up";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Up";
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead to the level above.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return AbstractMazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        final Application app = DDRemix.getApplication();
        app.getGameManager().goToLevelOffset(-1);
        SoundManager.playSound(SoundConstants.SOUND_UP);
    }

    @Override
    public int getMinimumRequiredQuantity(final Maze maze) {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return 0;
        } else {
            return PartyManager.getParty().getDungeonLevel() + 1;
        }
    }

    @Override
    public int getMaximumRequiredQuantity(final Maze maze) {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return 0;
        } else {
            return PartyManager.getParty().getDungeonLevel() + 1;
        }
    }

    @Override
    public boolean isRequired() {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        if (PartyManager.getParty().getDungeonLevel() == 0) {
            return false;
        } else {
            return true;
        }
    }
}
