/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractWall;
import com.puttysoftware.fantastlex.maze.utilities.ArrowTypeConstants;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.maze.utilities.TypeConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

public class IcedBarrierGenerator extends AbstractWall {
    // Constants
    private static final int TIMER_DELAY = 24;

    // Constructors
    public IcedBarrierGenerator() {
        super(ColorConstants.COLOR_CYAN);
        this.activateTimer(IcedBarrierGenerator.TIMER_DELAY);
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // De-ice
        final Application app = FantastleX.getApplication();
        final int pz = app.getMazeManager().getMaze().getPlayerLocationZ();
        final BarrierGenerator bg = new BarrierGenerator();
        app.getGameManager().morph(bg, dirX, dirY, pz);
        bg.timerExpiredAction(dirX, dirY);
    }

    @Override
    public boolean arrowHitAction(final int locX, final int locY,
            final int locZ, final int dirX, final int dirY, final int arrowType,
            final MazeObjectInventory inv) {
        if (arrowType == ArrowTypeConstants.ARROW_TYPE_ICE) {
            // Extend iced effect, if arrow was an ice arrow
            this.extendTimer(IcedBarrierGenerator.TIMER_DELAY);
        } else {
            // Else, de-ice
            final Application app = FantastleX.getApplication();
            final BarrierGenerator bg = new BarrierGenerator();
            app.getGameManager().morph(bg, locX, locY, locZ);
            bg.timerExpiredAction(locX, locY);
        }
        return false;
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BARRIER_GENERATOR;
    }

    @Override
    public String getName() {
        return "Iced Barrier Generator";
    }

    @Override
    public String getPluralName() {
        return "Iced Barrier Generators";
    }

    @Override
    public String getDescription() {
        return "Iced Barrier Generators are Barrier Generators that have been hit by an Ice Arrow or Ice Bomb.";
    }

    @Override
    protected void setTypes() {
        super.setTypes();
        this.type.set(TypeConstants.TYPE_REACTS_TO_ICE);
        this.type.set(TypeConstants.TYPE_REACTS_TO_FIRE);
        this.type.set(TypeConstants.TYPE_REACTS_TO_POISON);
        this.type.set(TypeConstants.TYPE_GENERATOR);
    }
}
