/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractAntiObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class NoPlayer extends AbstractAntiObject {
    // Constructors
    public NoPlayer() {
        super();
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_NO);
        this.setAttributeTemplateColor(ColorConstants.COLOR_RED);
    }

    @Override
    public final int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_PLAYER;
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().backUpPlayer(this);
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "No Player";
    }

    @Override
    public String getPluralName() {
        return "No Players";
    }

    @Override
    public String getDescription() {
        return "No Players prevent players from attempting to pass through.";
    }
}