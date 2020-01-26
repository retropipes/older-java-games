/*  TallerTower: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: TallerTower@worldwizard.net
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.game.GameLogicManager;
import com.puttysoftware.tallertower.maze.abc.AbstractTrap;
import com.puttysoftware.tallertower.maze.effects.MazeEffectConstants;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundManager;

public class Amulet extends AbstractTrap {
    // Constructors
    public Amulet() {
        super(ObjectImageConstants.OBJECT_IMAGE_AMULET);
    }

    @Override
    public String getName() {
        return "Amulet";
    }

    @Override
    public String getPluralName() {
        return "Amulets";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        TallerTower.getApplication().showMessage("You no longer slide on ice!");
        final GameLogicManager glm = TallerTower.getApplication()
                .getGameManager();
        glm.activateEffect(MazeEffectConstants.EFFECT_STICKY);
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Amulets make you not slide on ice for 15 steps when stepped on.";
    }
}