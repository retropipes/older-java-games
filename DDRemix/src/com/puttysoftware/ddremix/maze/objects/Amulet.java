/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DDRemix@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.game.GameLogicManager;
import com.puttysoftware.ddremix.maze.abc.AbstractTrap;
import com.puttysoftware.ddremix.maze.effects.MazeEffectConstants;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;

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
        DDRemix.getApplication().showMessage("You no longer slide on ice!");
        final GameLogicManager glm = DDRemix.getApplication().getGameManager();
        glm.activateEffect(MazeEffectConstants.EFFECT_STICKY);
        SoundManager.playSound(SoundConstants.SOUND_GRAB);
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Amulets make you not slide on ice for 15 steps when stepped on.";
    }
}