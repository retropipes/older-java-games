/*  TallerTower: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: TallerTower@worldwizard.net
 */
package com.puttysoftware.tallertower.maze.objects;

import com.puttysoftware.randomrange.RandomRange;
import com.puttysoftware.tallertower.TallerTower;
import com.puttysoftware.tallertower.game.GameLogicManager;
import com.puttysoftware.tallertower.maze.Maze;
import com.puttysoftware.tallertower.maze.abc.AbstractMPModifier;
import com.puttysoftware.tallertower.maze.effects.MazeEffectConstants;
import com.puttysoftware.tallertower.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundConstants;
import com.puttysoftware.tallertower.resourcemanagers.SoundManager;

public class LightGem extends AbstractMPModifier {
    // Constructors
    public LightGem() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_LIGHT_GEM;
    }

    @Override
    public String getName() {
        return "Light Gem";
    }

    @Override
    public String getPluralName() {
        return "Light Gems";
    }

    @Override
    public String getDescription() {
        return "Light Gems regenerate MP.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        TallerTower.getApplication().showMessage("Your power gathers!");
        TallerTower.getApplication().getGameManager()
                .activateEffect(MazeEffectConstants.EFFECT_POWER_GATHER);
        SoundManager.playSound(SoundConstants.SOUND_FOCUS);
        GameLogicManager.decay();
    }

    @Override
    public boolean shouldGenerateObject(final Maze maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Light Gems at 30% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 30;
    }
}
