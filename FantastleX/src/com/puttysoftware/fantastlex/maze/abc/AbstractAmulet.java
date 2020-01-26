/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.abc;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public abstract class AbstractAmulet extends AbstractInventoryableObject {
    // Fields
    private static final long SCORE_INCREASE = 25L;

    // Constructors
    protected AbstractAmulet(final int tc) {
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
            final MazeObjectInventory inv) {
        final Application app = FantastleX.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        FantastleX.getApplication().getGameManager()
                .addToScore(AbstractAmulet.SCORE_INCREASE);
        this.postMoveActionHook();
        inv.addItem(this);
    }

    public abstract void postMoveActionHook();
}
