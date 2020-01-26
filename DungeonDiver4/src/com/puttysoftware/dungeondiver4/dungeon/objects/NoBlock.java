/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractAntiObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class NoBlock extends AbstractAntiObject {
    // Constructors
    public NoBlock() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_NO);
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BLOCK_BASE;
    }

    @Override
    public void pushIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int z) {
        // Destroy incoming block
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().morph(this, x, y, z,
                DungeonConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public void pullIntoAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int z) {
        // Destroy incoming block
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().morph(this, x, y, z,
                DungeonConstants.LAYER_OBJECT);
        SoundManager.playSound(SoundConstants.SOUND_DESTROY);
    }

    @Override
    public String getName() {
        return "No Block";
    }

    @Override
    public String getPluralName() {
        return "No Blocks";
    }

    @Override
    public String getDescription() {
        return "No Blocks destroy any blocks that attempt to pass through.";
    }
}