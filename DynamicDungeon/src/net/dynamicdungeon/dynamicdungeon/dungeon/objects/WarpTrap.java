/*  DynamicDungeon: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DynamicDungeon@worldwizard.net
 */
package net.dynamicdungeon.dynamicdungeon.dungeon.objects;

import net.dynamicdungeon.dynamicdungeon.Application;
import net.dynamicdungeon.dynamicdungeon.DynamicDungeon;
import net.dynamicdungeon.dynamicdungeon.dungeon.abc.AbstractTrap;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.ObjectImageConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundConstants;
import net.dynamicdungeon.dynamicdungeon.resourcemanagers.SoundManager;
import net.dynamicdungeon.randomrange.RandomRange;

public class WarpTrap extends AbstractTrap {
    // Constructors
    public WarpTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_WARP_TRAP);
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
            final int dirY) {
        RandomRange rr, rc, rf;
        final Application app = DynamicDungeon.getApplication();
        int maxRow, maxCol, maxFloor, rRow, rCol, rFloor;
        maxRow = app.getDungeonManager().getDungeon().getRows() - 1;
        rr = new RandomRange(0, maxRow);
        maxCol = app.getDungeonManager().getDungeon().getColumns() - 1;
        rc = new RandomRange(0, maxCol);
        maxFloor = app.getDungeonManager().getDungeon().getFloors() - 1;
        rf = new RandomRange(0, maxFloor);
        app.getGameManager();
        do {
            rRow = rr.generate();
            rCol = rc.generate();
            rFloor = rf.generate();
        } while (app.getGameManager().tryUpdatePositionAbsolute(rRow, rCol,
                rFloor));
        app.getGameManager().updatePositionAbsolute(rRow, rCol, rFloor);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getDescription() {
        return "Warp Traps send anything that steps on one to a random location.";
    }
}
