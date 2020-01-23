/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: mazer5d@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.abc;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;
import com.puttysoftware.mazerunner2.maze.utilities.TypeConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundConstants;
import com.puttysoftware.mazerunner2.resourcemanagers.SoundManager;

public abstract class AbstractAmulet extends AbstractInventoryableObject {
    // Fields
    private static final long SCORE_INCREASE = 25L;

    // Constructors
    protected AbstractAmulet(int tc) {
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
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
        app.getGameManager().decay();
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        MazeRunnerII.getApplication().getGameManager()
                .addToScore(AbstractAmulet.SCORE_INCREASE);
        this.postMoveActionHook();
        inv.addItem(this);
    }

    public abstract void postMoveActionHook();
}
