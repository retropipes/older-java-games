/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.Dungeon;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractWall;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;

public class FadingWall extends AbstractWall {
    // Fields
    private static final int SCAN_LIMIT = 3;

    // Constructors
    public FadingWall() {
        super(ColorConstants.COLOR_LIGHT_GRAY);
        this.activateTimer(1);
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Disappear if the player is close to us
        boolean scanResult = false;
        final Application app = DungeonDiver4.getApplication();
        final int tx = app.getDungeonManager().getDungeon()
                .getPlayerLocationX();
        final int ty = app.getDungeonManager().getDungeon()
                .getPlayerLocationY();
        final int pz = app.getDungeonManager().getDungeon()
                .getPlayerLocationZ();
        scanResult = app.getDungeonManager().getDungeon().radialScan(dirX, dirY,
                FadingWall.SCAN_LIMIT, tx, ty);
        if (scanResult) {
            app.getGameManager().morph(new Empty(), dirX, dirY, pz);
        }
        this.activateTimer(1);
    }

    @Override
    public int getGameTemplateColor() {
        return ColorConstants.COLOR_BROWN;
    }

    @Override
    public int getAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_FADING;
    }

    @Override
    public int getAttributeTemplateColor() {
        return ColorConstants.COLOR_GRAY;
    }

    @Override
    public int getGameAttributeID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public int getGameAttributeTemplateColor() {
        return ColorConstants.COLOR_NONE;
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon dungeon, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Blacklist object
        return false;
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
