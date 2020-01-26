/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.DungeonConstants;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractMovableObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class PullableBlockOnce extends AbstractMovableObject {
    // Constructors
    public PullableBlockOnce() {
        super(false, true, ObjectImageConstants.OBJECT_IMAGE_ONCE);
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Pullable Block Once";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks Once";
    }

    @Override
    public void pullAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int pushX, final int pushY) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pushX, pushY, this);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
        app.getGameManager().morphOther(new Wall(), pushX, pushY,
                DungeonConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks Once can only be pulled once, before turning into a wall.";
    }
}