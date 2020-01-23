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

public class ConfusionTrap extends AbstractTrap {
    // Constructors
    public ConfusionTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_CONFUSION_TRAP);
    }

    @Override
    public String getName() {
        return "Confusion Trap";
    }

    @Override
    public String getPluralName() {
        return "Confusion Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        TallerTower.getApplication().showMessage("You are confused!");
        TallerTower.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_CONFUSED);
        SoundManager.playSound(SoundConstants.SOUND_CONFUSED);
    }

    @Override
    public String getDescription() {
        return "Confusion Traps randomly alter your controls for 6 steps when stepped on.";
    }
}