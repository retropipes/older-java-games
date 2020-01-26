/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractInvisibleTeleport;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class InvisibleOneShotTeleport extends AbstractInvisibleTeleport {
    // Constructors
    public InvisibleOneShotTeleport() {
        super(0, 0, 0, ObjectImageConstants.OBJECT_IMAGE_ONE_SHOT);
    }

    public InvisibleOneShotTeleport(final int destinationRow,
            final int destinationColumn, final int destinationFloor) {
        super(destinationRow, destinationColumn, destinationFloor,
                ObjectImageConstants.OBJECT_IMAGE_ONE_SHOT);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX, final int dirY,
            final DungeonObjectInventory inv) {
        final Application app = DungeonDiver4.getApplication();
        app.getGameManager().decay();
        app.getGameManager().updatePositionAbsolute(this.getDestinationRow(),
                this.getDestinationColumn(), this.getDestinationFloor());
        DungeonDiver4.getApplication().showMessage("Invisible Teleport!");
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "Invisible One-Shot Teleport";
    }

    @Override
    public String getGameName() {
        return "Empty";
    }

    @Override
    public String getPluralName() {
        return "Invisible One-Shot Teleports";
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        final DungeonEditorLogic me = DungeonDiver4.getApplication()
                .getEditor();
        return me.editTeleportDestination(
                DungeonEditorLogic.TELEPORT_TYPE_INVISIBLE_ONESHOT);
    }

    @Override
    public String getDescription() {
        return "Invisible One-Shot Teleports are a combination of invisible and one-shot teleport behaviors.";
    }
}