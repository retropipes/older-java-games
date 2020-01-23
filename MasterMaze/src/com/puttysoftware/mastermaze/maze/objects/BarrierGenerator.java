/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.game.GameLogicManager;
import com.puttysoftware.mastermaze.maze.generic.ArrowTypeConstants;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericGenerator;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;

public class BarrierGenerator extends GenericGenerator {
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
    protected boolean preMoveActionHook(final int dirX, final int dirY,
            final int dirZ, final int dirW) {
        return true;
    }

    @Override
    protected void arrowHitActionHook(final int locX, final int locY,
            final int locZ, final int arrowType, final ObjectInventory inv) {
        final GameLogicManager gm = MasterMaze.getApplication()
                .getGameManager();
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
