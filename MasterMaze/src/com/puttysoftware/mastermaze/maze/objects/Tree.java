/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericInfiniteLock;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class Tree extends GenericInfiniteLock {
    // Constructors
    public Tree() {
        super(new Axe());
        this.setTemplateColor(ColorConstants.COLOR_GREEN);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TREE;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallySolid(inv)) {
            MasterMaze.getApplication().showMessage("You need an axe");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (!this.getKey().isInfinite()) {
            inv.removeItem(this.getKey());
        }
        final Application app = MasterMaze.getApplication();
        app.getGameManager().decayTo(new CutTree());
        SoundManager.playSound(SoundConstants.SOUND_UNLOCK);
    }

    @Override
    public String getName() {
        return "Tree";
    }

    @Override
    public String getPluralName() {
        return "Trees";
    }

    @Override
    public String getDescription() {
        return "Trees transform into Cut Trees when hit with an Axe.";
    }
}