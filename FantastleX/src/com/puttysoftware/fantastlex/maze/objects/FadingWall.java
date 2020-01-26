/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.Application;
import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractWall;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;

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
        final Application app = FantastleX.getApplication();
        final int tx = app.getMazeManager().getMaze().getPlayerLocationX();
        final int ty = app.getMazeManager().getMaze().getPlayerLocationY();
        final int pz = app.getMazeManager().getMaze().getPlayerLocationZ();
        scanResult = app.getMazeManager().getMaze().radialScan(dirX, dirY,
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
