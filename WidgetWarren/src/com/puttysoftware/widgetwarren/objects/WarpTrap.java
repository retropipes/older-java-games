/*  WidgetWarren: A Maze-Solving Game
Copyright (C) 2008-2014 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.widgetwarren.objects;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.widgetwarren.Application;
import com.puttysoftware.widgetwarren.WidgetWarren;
import com.puttysoftware.widgetwarren.game.ObjectInventory;
import com.puttysoftware.widgetwarren.generic.GenericTrap;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundConstants;
import com.puttysoftware.widgetwarren.resourcemanagers.SoundManager;

public class WarpTrap extends GenericTrap {
    // Fields
    private RandomRange rr, rc, rf;

    // Constructors
    public WarpTrap() {
        super();
    }

    @Override
    public String getName() {
        return "Warp Trap";
    }

    @Override
    public String getPluralName() {
        return "Warp Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        final Application app = WidgetWarren.getApplication();
        int maxRow, maxCol, maxFloor, rRow, rCol, rFloor;
        maxRow = app.getMazeManager().getMaze().getRows() - 1;
        this.rr = new RandomRange(0, maxRow);
        maxCol = app.getMazeManager().getMaze().getColumns() - 1;
        this.rc = new RandomRange(0, maxCol);
        maxFloor = app.getMazeManager().getMaze().getFloors() - 1;
        this.rf = new RandomRange(0, maxFloor);
        do {
            rRow = this.rr.generate();
            rCol = this.rc.generate();
            rFloor = this.rf.generate();
        } while (!app.getGameManager().tryUpdatePositionAbsolute(rRow, rCol,
                rFloor));
        app.getGameManager().updatePositionAbsolute(rRow, rCol, rFloor);
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getDescription() {
        return "Warp Traps send anything that steps on one to a random location.";
    }
}
