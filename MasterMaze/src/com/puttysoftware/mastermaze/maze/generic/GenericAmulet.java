/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.generic;

import com.puttysoftware.mastermaze.Application;
import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public abstract class GenericAmulet extends GenericInventoryableObject {
    // Fields
    private static final long SCORE_INCREASE = 25L;

    // Constructors
    protected GenericAmulet(final int tc) {
        super(false, 0);
        this.setTemplateColor(tc);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_AMULET;
    }

    @Override
    public abstract String getName();

    @Override
    protected void setTypes() {
        this.type.set(TypeConstants.TYPE_AMULET);
        this.type.set(TypeConstants.TYPE_INVENTORYABLE);
        this.type.set(TypeConstants.TYPE_CONTAINABLE);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final Application app = MasterMaze.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        MasterMaze.getApplication().getGameManager()
                .addToScore(GenericAmulet.SCORE_INCREASE);
        this.postMoveActionHook();
        inv.addItem(this);
    }

    public abstract void postMoveActionHook();
}
