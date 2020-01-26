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

public class UTurnTrap extends AbstractTrap {
    // Constructors
    public UTurnTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_U_TURN_TRAP);
    }

    @Override
    public String getName() {
        return "U Turn Trap";
    }

    @Override
    public String getPluralName() {
        return "U Turn Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DDRemix.getApplication()
                .showMessage("Your controls are turned around!");
        DDRemix.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_U_TURNED);
        SoundManager.playSound(SoundConstants.SOUND_CHANGE);
    }

    @Override
    public String getDescription() {
        return "U Turn Traps invert your controls for 6 steps when stepped on.";
    }
}