/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTimeModifier;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;

public class HalfHourglass extends GenericTimeModifier {
    // Fields
    private static final long SCORE_GRAB = 5L;

    // Constructors
    public HalfHourglass() {
        super();
    }

    @Override
    public String getName() {
        return "Half Hourglass";
    }

    @Override
    public String getPluralName() {
        return "Half Hourglasses";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        Mazer5D.getApplication().getGameManager().decay();
        Mazer5D.getApplication().getMazeManager().getMaze()
                .extendTimerByInitialValueHalved();
        SoundPlayer.playSound(SoundIndex.GRAB,
                SoundGroup.GAME);
        Mazer5D.getApplication().getGameManager()
                .addToScore(HalfHourglass.SCORE_GRAB);
    }

    @Override
    public String getDescription() {
        return "Half Hourglasses extend the time to solve the current level by half the initial value.";
    }
}
