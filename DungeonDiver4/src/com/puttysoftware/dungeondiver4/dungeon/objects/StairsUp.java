/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.Application;
import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractTeleport;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.editor.DungeonEditorLogic;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class StairsUp extends AbstractTeleport {
    // Constructors
    public StairsUp() {
        super(0, 0, 0, false, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    // For derived classes only
    protected StairsUp(final boolean doesAcceptPushInto) {
        super(doesAcceptPushInto, ObjectImageConstants.OBJECT_IMAGE_NONE);
        this.setTemplateColor(ColorConstants.COLOR_NONE);
        this.setAttributeTemplateColor(ColorConstants.COLOR_NONE);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_STAIRS_UP;
    }

    @Override
    public String getName() {
        return "Stairs Up";
    }

    @Override
    public String getPluralName() {
        return "Sets of Stairs Up";
    }

    @Override
    public int getDestinationRow() {
        Application app = DungeonDiver4.getApplication();
        return app.getDungeonManager().getDungeon().getPlayerLocationX();
    }

    @Override
    public int getDestinationColumn() {
        Application app = DungeonDiver4.getApplication();
        return app.getDungeonManager().getDungeon().getPlayerLocationY();
    }

    @Override
    public int getDestinationFloor() {
        Application app = DungeonDiver4.getApplication();
        return app.getDungeonManager().getDungeon().getPlayerLocationZ() + 1;
    }

    @Override
    public int getDestinationLevel() {
        Application app = DungeonDiver4.getApplication();
        return app.getDungeonManager().getDungeon().getPlayerLocationW();
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        Application app = DungeonDiver4.getApplication();
        app.getGameManager().updatePositionAbsoluteNoEvents(
                this.getDestinationRow(), this.getDestinationColumn(),
                this.getDestinationFloor(), this.getDestinationLevel());
        SoundManager.playSound(SoundConstants.SOUND_UP);
    }

    @Override
    public void editorPlaceHook() {
        DungeonEditorLogic me = DungeonDiver4.getApplication().getEditor();
        me.pairStairs(DungeonEditorLogic.STAIRS_UP);
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Stairs Up lead to the floor above.";
    }

    @Override
    public int getCustomFormat() {
        return 0;
    }

    @Override
    public int getCustomProperty(int propID) {
        return AbstractDungeonObject.DEFAULT_CUSTOM_VALUE;
    }

    @Override
    public void setCustomProperty(int propID, int value) {
        // Do nothing
    }
}
