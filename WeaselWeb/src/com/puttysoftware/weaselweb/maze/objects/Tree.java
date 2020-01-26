/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.Application;
import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericInfiniteLock;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class Tree extends GenericInfiniteLock {
    // Constructors
    public Tree() {
        super(new Axe());
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            WeaselWeb.getApplication().showMessage("You need an axe");
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        if (!this.getKey().isInfinite()) {
            inv.removeItem(this.getKey());
        }
        final Application app = WeaselWeb.getApplication();
        app.getGameManager().decayTo(new CutTree());
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_UNLOCK);
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