/*  Worldz: A World-Exploring Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: worldz@worldwizard.net
 */
package net.worldwizard.worldz.objects;

import net.worldwizard.worldz.Application;
import net.worldwizard.worldz.Worldz;
import net.worldwizard.worldz.generic.GenericWall;
import net.worldwizard.worldz.world.WorldConstants;

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
        final Application app = Worldz.getApplication();
        final int pz = app.getGameManager().getPlayerManager()
                .getPlayerLocationZ();
        final int pl = WorldConstants.LAYER_OBJECT;
        final String targetName = new Player().getName();
        scanResult = app.getWorldManager().getWorld().radialScan(dirX, dirY, pz,
                pl, FadingWall.SCAN_LIMIT, targetName);
        if (scanResult) {
            app.getGameManager().morph(new Empty(), dirX, dirY, pz);
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
}
