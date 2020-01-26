/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTrap;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class WarpTrap extends AbstractTrap {
    // Fields
    private RandomRange rr, rc, rf;

    // Constructors
    public WarpTrap() {
        super(ColorConstants.COLOR_LIGHT_GRAY,
                ObjectImageConstants.OBJECT_IMAGE_WARP,
                ColorConstants.COLOR_GRAY);
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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        int maxRow, maxCol, maxFloor, rRow, rCol, rFloor;
        maxRow = app.getDungeonManager().getDungeon().getRows() - 1;
        this.rr = new RandomRange(0, maxRow);
        maxCol = app.getDungeonManager().getDungeon().getColumns() - 1;
        this.rc = new RandomRange(0, maxCol);
        maxFloor = app.getDungeonManager().getDungeon().getFloors() - 1;
        this.rf = new RandomRange(0, maxFloor);
        do {
            rRow = this.rr.generate();
            rCol = this.rc.generate();
            rFloor = this.rf.generate();
        } while (!app.getGameManager().tryUpdatePositionAbsolute(rRow, rCol,
                rFloor));
        app.getGameManager().updatePositionAbsolute(rRow, rCol, rFloor);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getDescription() {
        return "Warp Traps send anything that steps on one to a random location.";
    }
}
