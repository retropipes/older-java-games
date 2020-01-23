/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.randomnumbers.RandomRange;
import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.PreferencesManager;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.game.ObjectInventory;
import net.worldwizard.worldz.generic.GenericTrap;

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
        final Application app = Worldz.getApplication();
        int maxRow, maxCol, maxFloor, rRow, rCol, rFloor;
        maxRow = app.getWorldManager().getWorld().getRows() - 1;
        this.rr = new RandomRange(0, maxRow);
        maxCol = app.getWorldManager().getWorld().getColumns() - 1;
        this.rc = new RandomRange(0, maxCol);
        maxFloor = app.getWorldManager().getWorld().getFloors() - 1;
        this.rf = new RandomRange(0, maxFloor);
        do {
            rRow = this.rr.generate();
            rCol = this.rc.generate();
            rFloor = this.rf.generate();
        } while (!app.getGameManager().tryUpdatePositionAbsolute(rRow, rCol,
                rFloor));
        app.getGameManager().updatePositionAbsolute(rRow, rCol, rFloor);
        if (Worldz.getApplication().getPrefsManager()
                .getSoundEnabled(PreferencesManager.SOUNDS_GAME)) {
            this.playMoveSuccessSound();
        }
    }

    @Override
    public String getDescription() {
        return "Warp Traps send anything that steps on one to a random location.";
    }

    @Override
    public String getMoveSuccessSoundName() {
        return "teleport";
    }
}
