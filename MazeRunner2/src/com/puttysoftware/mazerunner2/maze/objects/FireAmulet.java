/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractAmulet;
import com.puttysoftware.mazerunner2.maze.effects.MazeEffectConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;

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
        final int x = MazeRunnerII.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = MazeRunnerII.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = MazeRunnerII.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MazeRunnerII.getApplication().getMazeManager().getMaze().hotGround(x, y,
                z);
    }

    @Override
    public void postMoveActionHook() {
        MazeRunnerII.getApplication().getGameManager().activateEffect(
                MazeEffectConstants.EFFECT_FIERY, FireAmulet.EFFECT_DURATION);
    }
}