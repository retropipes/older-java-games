/*  BrainMaze: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.brainmaze.objects;

import com.puttysoftware.brainmaze.game.ObjectInventory;
import com.puttysoftware.brainmaze.generic.ColorConstants;
import com.puttysoftware.brainmaze.generic.GenericGround;
import com.puttysoftware.brainmaze.resourcemanagers.SoundConstants;
import com.puttysoftware.brainmaze.resourcemanagers.SoundManager;

public class BridgedWaterVertical extends GenericGround {
    // Constructors
    public BridgedWaterVertical() {
        super(ColorConstants.COLOR_WATER);
        this.setAttributeName("bridge_ns");
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
    }

    @Override
    public String getName() {
        return "Bridged Water Vertical";
    }

    @Override
    public String getPluralName() {
        return "Squares of Bridged Water Vertical";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Bridged Water Vertical, unlike Water, can be walked on.";
    }
}
