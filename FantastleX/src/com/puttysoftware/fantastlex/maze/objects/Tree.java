/*  FantastleX: A Maze/RPG Hybrid Game
Copyleft 2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractInfiniteLock;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class Tree extends AbstractInfiniteLock {
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
            final int dirY, final MazeObjectInventory inv) {
        if (this.isConditionallySolid(inv)) {
            FantastleX.getApplication().showMessage("You need an axe");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        if (!this.getKey().isInfinite()) {
            inv.removeItem(this.getKey());
        }
        final Application app = FantastleX.getApplication();
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