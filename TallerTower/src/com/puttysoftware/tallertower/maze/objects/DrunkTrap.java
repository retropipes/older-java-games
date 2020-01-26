/*  TallerTower: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: TallerTower@worldwizard.net
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.maze.abc.AbstractTrap;
import com.puttysoftware.tallertower.maze.effects.MazeEffectConstants;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundManager;

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
        TallerTower.getApplication()
                .showMessage("You stumble around drunkenly!");
        TallerTower.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_DRUNK);
        SoundManager.playSound(SoundConstants.SOUND_DRUNK);
    }

    @Override
    public String getDescription() {
        return "Drunk Traps alter your movement in a way that resembles being intoxicated for 9 steps when stepped on.";
    }
}