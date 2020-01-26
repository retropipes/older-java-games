/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.generic.GenericAmulet;
import com.puttysoftware.mazer5d.maze.effects.MazeEffectConstants;

public class FireAmulet extends GenericAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public FireAmulet() {
        super();
    }

    @Override
    public String getName() {
        return "Fire Amulet";
    }

    @Override
    public String getPluralName() {
        return "Fire Amulets";
    }

    @Override
    public String getDescription() {
        return "Fire Amulets grant the power to transform ground into Hot Rock for 30 steps. Note that you can only wear one amulet at once.";
    }

    @Override
    public void stepAction() {
        final int x = Mazer5D.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = Mazer5D.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = Mazer5D.getApplication().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        Mazer5D.getApplication().getMazeManager().getMaze().hotGround(x, y, z);
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_FIERY, FireAmulet.EFFECT_DURATION);
    }
}