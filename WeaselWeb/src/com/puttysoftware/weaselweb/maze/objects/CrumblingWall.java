/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericWall;
import com.puttysoftware.weaselweb.maze.generic.TypeConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class CrumblingWall extends GenericWall {
    // Constructors
    public CrumblingWall() {
        super();
        this.setType(TypeConstants.TYPE_BREAKABLE_WALL);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY,
            final int arrowType, final ObjectInventory inv) {
        this.moveFailedAction(true, locX, locY, inv);
        return false;
    }

    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        // Destroy the wall
        final int pz = WeaselWeb.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        WeaselWeb.getApplication().getGameManager()
                .morph(new Empty(), dirX, dirY, pz);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CRACK);
    }

    @Override
    public String getName() {
        return "Crumbling Wall";
    }

    @Override
    public String getPluralName() {
        return "Crumbling Walls";
    }

    @Override
    public String getDescription() {
        return "Crumbling Walls crumble to nothing when hit.";
    }
}