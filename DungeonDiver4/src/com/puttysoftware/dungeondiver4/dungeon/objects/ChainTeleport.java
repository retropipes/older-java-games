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
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class ChainTeleport extends AbstractTeleport {
    // Constructors
    public ChainTeleport() {
        super(0, 0, 0, true, ObjectImageConstants.OBJECT_IMAGE_CHAIN);
    }

    public ChainTeleport(final int destinationRow, final int destinationColumn,
            final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor, true,
                ObjectImageConstants.OBJECT_IMAGE_CHAIN);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "Chain Teleport";
    }

    @Override
    public String getPluralName() {
        return "Chain Teleports";
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        final DungeonEditorLogic me = DungeonDiver4.getApplication()
                .getEditor();
        return me.editTeleportDestination(
                DungeonEditorLogic.TELEPORT_TYPE_CHAIN);
    }

    @Override
    public String getDescription() {
        return "Chain Teleports send you to a predetermined destination when stepped on.";
    }
}