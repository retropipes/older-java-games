/*  MazeRunnerII: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MazeRunnerII@worldwizard.net
 */
package com.puttysoftware.mazerunner2.maze.objects;

import com.puttysoftware.mazerunner2.Application;
import com.puttysoftware.mazerunner2.MazeRunnerII;
import com.puttysoftware.mazerunner2.maze.abc.AbstractGenerator;
import com.puttysoftware.mazerunner2.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.mazerunner2.maze.utilities.ColorConstants;
import com.puttysoftware.mazerunner2.maze.utilities.MazeObjectInventory;

public class PoisonedBarrierGenerator extends AbstractGenerator {
    // Fields
    private int POISON_CYCLES;
    private static final int POISON_LIMIT = 2;

    // Constructors
    public PoisonedBarrierGenerator() {
        super(ColorConstants.COLOR_GREEN);
        this.TIMER_DELAY = 18;
        this.POISON_CYCLES = 0;
    }

    @Override
    public String getName() {
        return "Poisoned Barrier Generator";
    }

    @Override
    public String getPluralName() {
        return "Poisoned Barrier Generators";
    }

    @Override
    public String getDescription() {
        return "Poisoned Barrier Generators create Barriers. When hit or shot, they stop generating for a while, then resume generating slower than normal.";
    }

    @Override
    protected boolean preMoveActionHook(int dirX, int dirY, int dirZ, int dirW) {
        this.POISON_CYCLES++;
        if (this.POISON_CYCLES == PoisonedBarrierGenerator.POISON_LIMIT) {
            Application app = MazeRunnerII.getApplication();
            BarrierGenerator bg = new BarrierGenerator();
            app.getGameManager().morph(bg, dirX, dirY, dirZ);
            bg.timerExpiredAction(dirX, dirY);
        }
        return true;
    }

    @Override
    protected void arrowHitActionHook(int locX, int locY, int locZ,
            int arrowType, MazeObjectInventory inv) {
        Application app = MazeRunnerII.getApplication();
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            app.getGameManager().morph(new IcedBarrierGenerator(), locX, locY,
                    locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_FIRE) {
            app.getGameManager().morph(new EnragedBarrierGenerator(), locX,
                    locY, locZ);
        } else if (arrowType == ArrowTypeConstants.ARROW_TYPE_SHOCK) {
            app.getGameManager().morph(new ShockedBarrierGenerator(), locX,
                    locY, locZ);
        } else {
            this.preMoveAction(false, locX, locY, inv);
        }
    }
}
