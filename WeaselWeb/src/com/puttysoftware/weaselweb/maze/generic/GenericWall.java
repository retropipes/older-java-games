/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.generic;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.MazeConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public abstract class GenericWall extends MazeObject {
    // Constructors
    protected GenericWall() {
        super();
        this.getSolidProperties().setSolid(true);
        this.setType(TypeConstants.TYPE_WALL);
    }

    protected GenericWall(final boolean isSolidXN, final boolean isSolidXS,
            final boolean isSolidXE, final boolean isSolidXW,
            final boolean isSolidIN, final boolean isSolidIS,
            final boolean isSolidIE, final boolean isSolidIW) {
        super();
        this.getSolidProperties().setDirectionallySolid(true,
                DirectionConstants.DIRECTION_NORTH, isSolidXN);
        this.getSolidProperties().setDirectionallySolid(true,
                DirectionConstants.DIRECTION_SOUTH, isSolidXS);
        this.getSolidProperties().setDirectionallySolid(true,
                DirectionConstants.DIRECTION_EAST, isSolidXE);
        this.getSolidProperties().setDirectionallySolid(true,
                DirectionConstants.DIRECTION_WEST, isSolidXW);
        this.getSolidProperties().setDirectionallySolid(false,
                DirectionConstants.DIRECTION_NORTH, isSolidIN);
        this.getSolidProperties().setDirectionallySolid(false,
                DirectionConstants.DIRECTION_SOUTH, isSolidIS);
        this.getSolidProperties().setDirectionallySolid(false,
                DirectionConstants.DIRECTION_EAST, isSolidIE);
        this.getSolidProperties().setDirectionallySolid(false,
                DirectionConstants.DIRECTION_WEST, isSolidIW);
        this.setType(TypeConstants.TYPE_WALL);
    }

    protected GenericWall(final boolean isDestroyable,
            final boolean doesChainReact) {
        super();
        this.getSolidProperties().setSolid(true);
        this.setDestroyable(isDestroyable);
        this.setChainReact(doesChainReact);
        this.setType(TypeConstants.TYPE_WALL);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Do nothing
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        WeaselWeb.getApplication().showMessage("Can't go that way");
        // Play move failed sound, if it's enabled
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public abstract String getName();

    @Override
    public int getLayer() {
        return MazeConstants.LAYER_OBJECT;
    }

    @Override
    public int getCustomProperty(final int propID) {
        return MazeObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(final int propID, final int value) {
        // Do nothing
    }
}