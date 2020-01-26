/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractGround;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class BridgedLavaHorizontal extends AbstractGround {
    // Constructors
    public BridgedLavaHorizontal() {
        super(ColorConstants.COLOR_ORANGE);
        this.setAttributeID(
                ObjectImageConstants.OBJECT_IMAGE_BRIDGE_HORIZONTAL);
        this.setAttributeTemplateColor(ColorConstants.COLOR_BRIDGE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        SoundManager.playSound(SoundConstants.SOUND_WALK);
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
