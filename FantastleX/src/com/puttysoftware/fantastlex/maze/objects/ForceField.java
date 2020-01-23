/*  FantastleX: A Maze/RPG Hybrid Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: FantastleX@worldwizard.net
 */
package com.puttysoftware.fantastlex.maze.objects;

import com.puttysoftware.fantastlex.FantastleX;
import com.puttysoftware.fantastlex.maze.abc.AbstractField;
import com.puttysoftware.fantastlex.maze.utilities.ColorConstants;
import com.puttysoftware.fantastlex.maze.utilities.MazeObjectInventory;
import com.puttysoftware.fantastlex.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundConstants;
import com.puttysoftware.fantastlex.resourcemanagers.SoundManager;

public class ForceField extends AbstractField {
    // Constructors
    public ForceField() {
        super(new EnergySphere(), false, ColorConstants.COLOR_NONE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final MazeObjectInventory inv) {
        FantastleX.getApplication().showMessage("You'll get zapped");
        SoundManager.playSound(SoundConstants.SOUND_FORCE_FIELD);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_FORCE_FIELD;
    }

    @Override
    public String getName() {
        return "Force Field";
    }

    @Override
    public String getPluralName() {
        return "Force Fields";
    }

    @Override
    public String getDescription() {
        return "Force Fields block movement without an Energy Sphere.";
    }
}
