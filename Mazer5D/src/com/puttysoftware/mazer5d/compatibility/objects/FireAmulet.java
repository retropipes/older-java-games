/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericAmulet;
import com.puttysoftware.mazer5d.compatibility.maze.effects.MazeEffectConstants;

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
        final int x = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationX();
        final int y = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationY();
        final int z = Mazer5D.getBagOStuff().getGameManager()
                .getPlayerManager().getPlayerLocationZ();
        Mazer5D.getBagOStuff().getMazeManager().getMaze().hotGround(x, y, z);
    }

    @Override
    public void postMoveActionHook() {
        Mazer5D.getBagOStuff().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_FIERY, FireAmulet.EFFECT_DURATION);
    }
}