/*  loopchute: A Maze-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.loopchute.objects;

import com.puttysoftware.loopchute.game.ObjectInventory;
import com.puttysoftware.loopchute.generic.ColorConstants;
import com.puttysoftware.loopchute.generic.GenericGround;
import com.puttysoftware.loopchute.resourcemanagers.SoundConstants;
import com.puttysoftware.loopchute.resourcemanagers.SoundManager;

public class BridgedLavaHorizontal extends GenericGround {
    // Constructors
    public BridgedLavaHorizontal() {
        super(ColorConstants.COLOR_ORANGE);
        this.setAttributeName("bridge_ew");
        this.setAttributeTemplateColor(ColorConstants.COLOR_BROWN);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final ObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_CATEGORY_SOLVING_MAZE,
                SoundConstants.SOUND_WALK);
    }

    @Override
    public String getName() {
        return "Bridged Lava Horizontal";
    }

    @Override
    public String getPluralName() {
        return "Squares of Bridged Lava Horizontal";
    }

    @Override
    public boolean overridesDefaultPostMove() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Bridged Lava Horizontal, unlike Lava, can be walked on.";
    }
}
