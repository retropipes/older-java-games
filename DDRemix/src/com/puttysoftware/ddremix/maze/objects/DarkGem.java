/*  DDRemix: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DDRemix@worldwizard.net
 */
package com.puttysoftware.ddremix.maze.objects;

import com.puttysoftware.ddremix.DDRemix;
import com.puttysoftware.ddremix.game.GameLogicManager;
import com.puttysoftware.ddremix.maze.Maze;
import com.puttysoftware.ddremix.maze.abc.AbstractMPModifier;
import com.puttysoftware.ddremix.maze.effects.MazeEffectConstants;
import com.puttysoftware.ddremix.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundConstants;
import com.puttysoftware.ddremix.resourcemanagers.SoundManager;
import com.puttysoftware.randomrange.RandomRange;

public class DarkGem extends AbstractMPModifier {
    // Constructors
    public DarkGem() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_DARK_GEM;
    }

    @Override
    public String getName() {
        return "Dark Gem";
    }

    @Override
    public String getPluralName() {
        return "Dark Gems";
    }

    @Override
    public String getDescription() {
        return "Dark Gems take MP away.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY) {
        DDRemix.getApplication().showMessage("Your power withers!");
        DDRemix.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_POWER_WITHER);
        SoundManager.playSound(SoundConstants.SOUND_FOCUS);
        GameLogicManager.decay();
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Dark Gems at 30% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 30;
    }
}
