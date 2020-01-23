/*  RuleMazer: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: rulemazer@puttysoftware.com
 */
package com.puttysoftware.rulemazer.objects;

import com.puttysoftware.rulemazer.Application;
import com.puttysoftware.rulemazer.Main;
import com.puttysoftware.rulemazer.game.ObjectInventory;
import com.puttysoftware.rulemazer.generic.GenericInfiniteLock;
import com.puttysoftware.rulemazer.resourcemanagers.SoundConstants;
import com.puttysoftware.rulemazer.resourcemanagers.SoundManager;

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
            Main.getApplication().showMessage("You need an axe");
        }
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        if (!this.getKey().isInfinite()) {
            inv.removeItem(this.getKey());
        }
        final Application app = Main.getApplication();
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