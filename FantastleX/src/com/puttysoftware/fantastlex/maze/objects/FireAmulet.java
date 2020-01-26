/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractAmulet;
import com.puttysoftware.fantastlex.maze.effects.MazeEffectConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;

public class FireAmulet extends AbstractAmulet {
    // Constants
    private static final int EFFECT_DURATION = 30;

    // Constructors
    public FireAmulet() {
        super(ColorConstants.COLOR_RED);
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
        final int x = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = FantastleX.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        FantastleX.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }

    @Override
    public void postMoveActionHook() {
        FantastleX.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_FIERY, FireAmulet.EFFECT_DURATION);
    }
}