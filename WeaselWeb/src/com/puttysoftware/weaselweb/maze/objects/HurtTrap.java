/*  WeaselWeb: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.weaselweb.maze.objects;

import com.puttysoftware.weaselweb.WeaselWeb;
import com.puttysoftware.weaselweb.game.ObjectInventory;
import com.puttysoftware.weaselweb.maze.generic.GenericTrap;
import com.puttysoftware.weaselweb.resourcemanagers.SoundConstants;
import com.puttysoftware.weaselweb.resourcemanagers.SoundManager;

public class HurtTrap extends GenericTrap {
    // Fields
    private int damage;

    // Constructors
    public HurtTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        this.damage = WeaselWeb.getApplication().getMazeManager().getMaze()
                .getMaximumHP() / 50;
        if (this.damage < 1) {
            this.damage = 1;
        }
        WeaselWeb.getApplication().getMazeManager().getMaze()
                .doDamage(this.damage);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_BARRIER);
    }

    @Override
    public String getDescription() {
        return "Hurt Traps hurt you when stepped on.";
    }
}