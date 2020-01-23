/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.effects.MazeEffectConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericAmulet;

public class FireAmulet extends GenericAmulet {
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
        final int x = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationX();
        final int y = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationY();
        final int z = MasterMaze.getApplication().getMazeManager().getMaze()
                .getPlayerLocationZ();
        MasterMaze.getApplication().getMazeManager().getMaze()
                .hotGround(x, y, z);
    }

    @Override
    public void postMoveActionHook() {
        MasterMaze
                .getApplication()
                .getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_FIERY,
                        FireAmulet.EFFECT_DURATION);
    }
}