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

public class PullableBlockThrice extends AbstractMovableObject {
    // Constructors
    public PullableBlockThrice() {
        super(false, true, ObjectImageConstants.OBJECT_IMAGE_THRICE);
        this.setTemplateColor(ColorConstants.COLOR_GRAY);
    }

    @Override
    public String getName() {
        return "Pullable Block Thrice";
    }

    @Override
    public String getPluralName() {
        return "Pullable Blocks Thrice";
    }

    @Override
    public void pullAction(final DungeonObjectInventory inv,
            final AbstractDungeonObject mo, final int x, final int y,
            final int pushX, final int pushY) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePulledPosition(x, y, pushX, pushY, this);
        SoundManager.playSound(SoundConstants.SOUND_PUSH_PULL);
        app.getGameManager().morphOther(new PullableBlockTwice(), pushX, pushY,
                DungeonConstants.LAYER_OBJECT);
    }

    @Override
    public String getDescription() {
        return "Pullable Blocks Thrice can only be pulled three times, before turning into a wall.";
    }
}