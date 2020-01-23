/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericWall;
import com.puttysoftware.rulemazer.generic.TypeConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

public class DamagedWall extends GenericWall {
    // Constructors
    public DamagedWall() {
        super();
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
        final int pz = Main.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        Main.getApplication().getGameManager()
                .morph(new CrumblingWall(), dirX, dirY, pz);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_CRACK);
    }

    @Override
    public String getName() {
        return "Damaged Wall";
    }

    @Override
    public String getPluralName() {
        return "Damaged Walls";
    }

    @Override
    public String getDescription() {
        return "Damaged Walls turn into Crumbling Walls when hit.";
    }

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_BREAKABLE_WALL);
        this.type.set(TypeConstants.TYPE_WALL);
    }
}