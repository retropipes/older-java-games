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

public class DrunkTrap extends AbstractTrap {
    // Constructors
    public DrunkTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_DRUNK_TRAP);
    }

    @Override
    public String getName() {
        return "Drunk Trap";
    }

    @Override
    public String getPluralName() {
        return "Drunk Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        DDRemix.getApplication().showMessage("You stumble around drunkenly!");
        DDRemix.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_DRUNK);
        SoundManager.playSound(SoundConstants.SOUND_DRUNK);
    }

    @Override
    public String getDescription() {
        return "Drunk Traps alter your movement in a way that resembles being intoxicated for 9 steps when stepped on.";
    }
}