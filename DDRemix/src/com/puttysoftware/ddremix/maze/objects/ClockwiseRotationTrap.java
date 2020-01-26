/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DDRemix@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.maze.abc.AbstractTrap;
import com.puttysoftware.ddremix.maze.effects.MazeEffectConstants;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

public class ClockwiseRotationTrap extends AbstractTrap {
    // Constructors
    public ClockwiseRotationTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_CW_ROTATION_TRAP);
    }

    @Override
    public String getName() {
        return "Clockwise Rotation Trap";
    }

    @Override
    public String getPluralName() {
        return "Clockwise Rotation Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
        DDRemix.getApplication().showMessage("Your controls are rotated!");
        DDRemix.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_ROTATED_CLOCKWISE);
    }

    @Override
    public String getDescription() {
        return "Clockwise Rotation Traps rotate your controls clockwise for 6 steps when stepped on.";
    }
}