/*  Mazer5D: A Maze-Solving Game
Copyright (C) 2008-2013 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.mazer5d.compatibility.objects;

import com.puttysoftware.mazer5d.Mazer5D;
import com.puttysoftware.mazer5d.compatibility.abc.GenericWall;
import com.puttysoftware.mazer5d.gui.BagOStuff;
import com.puttysoftware.mazer5d.objectmodel.Layers;
import com.puttysoftware.mazer5d.objectmodel.MazeObjects;

public class FadingWall extends GenericWall {
    // Fields
    private static final int SCAN_LIMIT = 3;

    // Constructors
    public FadingWall() {
        super();
        this.activateTimer(1);
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Disappear if the player is close to us
        boolean scanResult = false;
        final BagOStuff app = Mazer5D.getBagOStuff();
        final int pz = app.getGameManager().getPlayerManager()
                .getPlayerLocationZ();
        final int pl = Layers.OBJECT;
        final String targetName = new Player().getName();
        scanResult = app.getMazeManager().getMaze().radialScan(dirX, dirY, pz,
                pl, FadingWall.SCAN_LIMIT, targetName);
        if (scanResult) {
            app.getGameManager().morph(GameObjects.getEmptySpace(), dirX, dirY,
                    pz);
        }
        this.activateTimer(1);
    }

    @Override
    public String getName() {
        return "Fading Wall";
    }

    @Override
    public String getGameName() {
        return "Wall";
    }

    @Override
    public String getPluralName() {
        return "Fading Walls";
    }

    @Override
    public String getDescription() {
        return "Fading Walls disappear when you get close to them.";
    }

    @Override
    public MazeObjects getUniqueID() {
        return MazeObjects.FADING_WALL;
    }
}
