/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTeleport;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class ControllableTeleport extends AbstractTeleport {
    // Constructors
    public ControllableTeleport() {
        super(0, 0, 0, false, ObjectImageConstants.OBJECT_IMAGE_CONTROLLABLE);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        SoundManager.playSound(SoundConstants.SOUND_WALK);
        app.getGameManager().controllableTeleport();
    }

    @Override
    public String getName() {
        return "Controllable Teleport";
    }

    @Override
    public String getPluralName() {
        return "Controllable Teleports";
    }

    @Override
    public void editorProbeHook() {
        DungeonDiver4.getApplication().showMessage(this.getName());
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Controllable Teleports let you choose the place you teleport to.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }
}