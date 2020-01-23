/*  MasterMaze: A Maze-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: MasterMaze@worldwizard.net
 */
package com.puttysoftware.mastermaze.maze.objects;

import com.puttysoftware.mastermaze.MasterMaze;
import com.puttysoftware.mastermaze.maze.generic.ColorConstants;
import com.puttysoftware.mastermaze.maze.generic.GenericField;
import com.puttysoftware.mastermaze.maze.generic.ObjectInventory;
import com.puttysoftware.mastermaze.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundConstants;
import com.puttysoftware.mastermaze.resourcemanagers.SoundManager;

public class ForceField extends GenericField {
    // Constructors
    public ForceField() {
        super(new EnergySphere(), false, ColorConstants.COLOR_NONE);
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        MasterMaze.getApplication().showMessage("You'll get zapped");
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
