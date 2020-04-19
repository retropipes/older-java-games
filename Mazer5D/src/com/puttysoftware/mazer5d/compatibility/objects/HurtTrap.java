/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTrap;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

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
        this.damage = Mazer5D.getBagOStuff().getMazeManager().getMaze()
                .getMaximumHP() / 50;
        if (this.damage < 1) {
            this.damage = 1;
        }
        Mazer5D.getBagOStuff().getMazeManager().getMaze().doDamage(this.damage);
        SoundPlayer.playSound(SoundIndex.BARRIER, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Hurt Traps hurt you when stepped on.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.HURT_TRAP;
    }
}