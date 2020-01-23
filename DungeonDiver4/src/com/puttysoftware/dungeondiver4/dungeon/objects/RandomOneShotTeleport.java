/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class RandomOneShotTeleport extends RandomTeleport {
    // Constructors
    public RandomOneShotTeleport() {
        super();
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_RANDOM_ONE_SHOT);
    }

    public RandomOneShotTeleport(final int newRandomRangeY,
            final int newRandomRangeX) {
        super(newRandomRangeY, newRandomRangeX);
        this.setAttributeID(ObjectImageConstants.OBJECT_IMAGE_RANDOM_ONE_SHOT);
    }

    // Scriptability
    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        app.getGameManager().decay();
        int dr, dc;
        do {
            dr = this.getDestinationRow();
            dc = this.getDestinationColumn();
        } while (!app.getGameManager().tryUpdatePositionRelative(dr, dc));
        app.getGameManager().updatePositionRelative(dr, dc, 0);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getName() {
        return "Random One-Shot Teleport";
    }

    @Override
    public String getPluralName() {
        return "Random One-Shot Teleports";
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        DungeonEditorLogic me = DungeonDiver4.getApplication().getEditor();
        return me
                .editTeleportDestination(DungeonEditorLogic.TELEPORT_TYPE_RANDOM_ONESHOT);
    }

    @Override
    public String getDescription() {
        return "Random One-Shot Teleports are random, and only work once.";
    }
}