/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTrap;
import com.puttysoftware.mazer5d.compatibility.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class DizzinessTrap extends GenericTrap {
    // Fields
    private static final int EFFECT_DURATION = 10;

    // Constructors
    public DizzinessTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Dizziness Trap";
    }

    @Override
    public String getPluralName() {
        return "Dizziness Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Mazer5D.getBagOStuff().showMessage("You feel dizzy!");
        Mazer5D.getBagOStuff().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_DIZZY,
                DizzinessTrap.EFFECT_DURATION);
        SoundPlayer.playSound(SoundIndex.DIZZY, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Dizziness Traps randomly alter your controls each step for 10 steps when stepped on.";
    }
}