/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.assets.SoundGroup;
import com.puttysoftware.mazer5d.assets.SoundIndex;
import com.puttysoftware.mazer5d.compatibility.abc.GenericTrap;
import com.puttysoftware.mazer5d.game.ObjectInventory;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.loaders.SoundPlayer;
import com.puttysoftware.randomrange.RandomRange;

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
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        final BagOStuff app = Mazer5D.getBagOStuff();
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
        SoundPlayer.playSound(SoundIndex.TELEPORT, SoundGroup.GAME);
    }

    @Override
    public String getDescription() {
        return "Warp Traps send anything that steps on one to a random location.";
    }
}
