/*  DungeonDiver4: A Dungeon-Solving Game
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: DungeonDiver4@worldwizard.net
 */
package com.puttysoftware.dungeondiver4.dungeon.objects;

import com.puttysoftware.dungeondiver4.DungeonDiver4;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractContainer;
import com.puttysoftware.dungeondiver4.dungeon.abc.AbstractDungeonObject;
import com.puttysoftware.dungeondiver4.dungeon.utilities.ColorConstants;
import com.puttysoftware.dungeondiver4.dungeon.utilities.DungeonObjectInventory;
import com.puttysoftware.dungeondiver4.resourcemanagers.ObjectImageConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundConstants;
import com.puttysoftware.dungeondiver4.resourcemanagers.SoundManager;

public class TreasureChest extends AbstractContainer {
    // Constructors
    public TreasureChest() {
        super(new TreasureKey());
        this.setTemplateColor(ColorConstants.COLOR_BRIDGE);
    }

    public TreasureChest(AbstractDungeonObject inside) {
        super(new TreasureKey(), inside);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_TREASURE_CHEST;
    }

    // Scriptability
    @Override
    public void moveFailedAction(final boolean ie, final int dirX,
            final int dirY, final DungeonObjectInventory inv) {
        if (this.isConditionallyDirectionallySolid(ie, dirX, dirY, inv)) {
            DungeonDiver4.getApplication().showMessage(
                    "You need a treasure key");
        }
        SoundManager.playSound(SoundConstants.SOUND_WALK_FAILED);
    }

    @Override
    public String getName() {
        return "Treasure Chest";
    }

    @Override
    public String getPluralName() {
        return "Treasure Chests";
    }

    @Override
    public AbstractDungeonObject editorPropertiesHook() {
        return DungeonDiver4.getApplication().getEditor()
                .editTreasureChestContents();
    }

    @Override
    public String getDescription() {
        return "Treasure Chests require Treasure Keys to open, and contain 1 other item.";
    }
}