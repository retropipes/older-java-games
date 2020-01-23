/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.game.GameLogicManager;
import com.puttysoftware.mazerunner2.maze.abc.AbstractGenerator;
import com.puttysoftware.mazerunner2.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;

public class BarrierGenerator extends AbstractGenerator {
    // Constructors
    public BarrierGenerator() {
        super(ColorConstants.COLOR_YELLOW);
    }

    @Override
    public String getName() {
        return "Barrier Generator";
    }

    @Override
    public String getPluralName() {
        return "Barrier Generators";
    }

    @Override
    public String getDescription() {
        return "Barrier Generators create Barriers. When hit or shot, they stop generating for a while, then resume generating.";
    }

    @Override
    protected boolean preMoveActionHook(int dirX, int dirY, int dirZ, int dirW) {
        return true;
    }

    @Override
    protected void arrowHitActionHook(int locX, int locY, int locZ,
            int arrowType, MazeObjectInventory inv) {
        GameLogicManager gm = MazeRunnerII.getApplication().getGameManager();
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            gm.morph(new IcedBarrierGenerator(), locX, locY, locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_FIRE) {
            gm.morph(new EnragedBarrierGenerator(), locX, locY, locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_POISON) {
            gm.morph(new PoisonedBarrierGenerator(), locX, locY, locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_SHOCK) {
            gm.morph(new ShockedBarrierGenerator(), locX, locY, locZ);
        } else {
            this.preMoveAction(false, locX, locY, inv);
        }
    }
}
